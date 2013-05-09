package server.service.impl;

import java.io.InputStream;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;

import server.domain.Image;
import server.domain.User;
import server.domain.User_;
import server.exception.InvalidParameterException;
import server.exception.MissingParameterException;
import server.exception.ServerException;
import server.logger.InjectLogger;
import server.service.ImageService;
import server.service.UserService;
import server.util.HashUtil;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class UserServiceImpl extends AbstractDomainServiceImpl<Integer, User> implements UserService {
	@InjectLogger
	private Logger logger;
	@Inject
	private ImageService imageService;
	private boolean indexed = false;

	public UserServiceImpl() {
		super(User.class);
	}

	@Override
	public User find(String username, String password) {
		logger.debug("Find User " + username);
		String passwordHash = hashPassword(password);
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);
		query.where(builder.and(builder.equal(builder.lower(root.get(User_.username)), username.toLowerCase()),
				builder.equal(root.get(User_.passwordHash), passwordHash)));
		try {
			TypedQuery<User> typedQuery = entityManager.get().createQuery(query);
			typedQuery.setHint("org.hibernate.cacheable", true);
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByUsername(String username) {
		logger.debug("Find User " + username);
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);
		query.where(builder.and(builder.equal(builder.lower(root.get(User_.username)), username.toLowerCase())));
		try {
			TypedQuery<User> typedQuery = entityManager.get().createQuery(query);
			typedQuery.setHint("org.hibernate.cacheable", true);
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByEmail(String email) {
		logger.debug("Find User " + email);
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);
		query.where(builder.and(builder.equal(builder.lower(root.get(User_.email)), email.toLowerCase())));
		try {
			TypedQuery<User> typedQuery = entityManager.get().createQuery(query);
			typedQuery.setHint("org.hibernate.cacheable", true);
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<User> list(String queryString, SortType sort, boolean reverse, Integer start, Integer count) {
		logger.debug("List User " + queryString);
		if (sort == null)
			sort = SortType.DEFAULT;
		if (start == null)
			start = 0;
		if (count == null || count > 100)
			count = 100;
		if (StringUtils.isEmpty(queryString))
			return list(sort, reverse, start, count);
		else
			return search(queryString, sort, reverse, start, count);
	}

	@Override
	public int count(String queryString) {
		if (StringUtils.isEmpty(queryString))
			return count();
		else
			return countSearch(queryString);
	}

	private List<User> list(SortType sort, boolean reverse, Integer start, Integer count) {
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);

		TypedQuery<User> typedQuery = entityManager.get().createQuery(query);
		typedQuery.setHint("org.hibernate.cacheable", true);
		typedQuery.setFirstResult(start);
		typedQuery.setMaxResults(count);
		return typedQuery.getResultList();
	}

	private List<User> search(String queryString, SortType sort, boolean reverse, Integer start, Integer count) {
		FullTextQuery query = buildQuery(queryString);
		query.setFirstResult(start);
		query.setMaxResults(count);
		@SuppressWarnings("unchecked")
		List<User> results = (List<User>) query.getResultList();

		return results;
	}

	private int countSearch(String queryString) {
		return buildQuery(queryString).getResultSize();
	}

	private FullTextQuery buildQuery(String queryString) {
		String[] fields = new String[] { "name", "username", "email", "bio" };

		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
		org.apache.lucene.search.Query query;
		if (queryString == null || queryString.length() == 0) {
			query = new MatchAllDocsQuery();
		} else {
			try {
				query = new MultiFieldQueryParser(Version.LUCENE_35, fields, new StandardAnalyzer(Version.LUCENE_35)).parse(queryString);
			} catch (ParseException e) {
				logger.warn("Could not parse query: " + queryString);
				return null;
			}
		}
		return fullTextEntityManager.createFullTextQuery(query, User.class);
	}

	private synchronized FullTextEntityManager getFullTextEntityManager() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager.get());
		if (!indexed) {
			try {
				fullTextEntityManager.createIndexer(User.class).startAndWait();
			} catch (InterruptedException e) {
				logger.error("Indexing failed", e);
			}
			indexed = true;
		}
		return fullTextEntityManager;
	}

	@Override
	@Transactional
	public void create(User user) {
		user.setPasswordHash(hashPassword(user.getPassword()));
		if (user.getImage() == null)
			user.setImage(imageService.getDefaultImage());
		super.create(user);
	}

	@Override
	@Transactional
	public void create(User user, InputStream imageStream) {
		user.setInitialized(false);
		validate(user);

		if (imageStream != null) {
			Image image = imageService.create(imageStream);
			user.setImage(image);
		}

		user.setInitialized(true);

		create(user);
	}

	@Override
	@Transactional
	public void modify(User user, InputStream imageStream) {
		if (imageStream != null) {
			Image image = imageService.create(imageStream);
			user.setImage(image);
		}

		modify(user);
	}

	@Override
	@Transactional
	public User modify(User user) {
		user.setPasswordHash(hashPassword(user.getPassword()));
		return super.modify(user);
	}

	public void validate(User user) {
		if (user.getName() == null)
			throw new MissingParameterException("name");
		if (user.getUsername() == null)
			throw new MissingParameterException("username");
		if (user.getEmail() == null)
			throw new MissingParameterException("email");
		if (!isValidEmail(user.getEmail()))
			throw new InvalidParameterException("email", "Invalid email address");
		if (user.getPassword() == null && user.getPasswordHash() == null)
			throw new MissingParameterException("password");
		if (user.getPassword() != null && user.getPassword().length() < 6)
			throw new InvalidParameterException("password", "Password is too short");
		User existingUser = findByUsername(user.getUsername());
		if (existingUser != null && !Objects.equal(existingUser.getId(), user.getId()))
			throw new ServerException("duplicate_username", "Another user with that username already exists");
		existingUser = findByEmail(user.getEmail());
		if (existingUser != null && !Objects.equal(existingUser.getId(), user.getId()))
			throw new ServerException("duplicate_email", "Another user with that email already exists");

	}

	private boolean isValidEmail(String email) {
		try {
			InternetAddress address = new InternetAddress(email);
			address.validate();
			return true;
		} catch (AddressException ex) {
			return false;
		}
	}

	private String hashPassword(String password) {
		if (password == null)
			return null;
		return HashUtil.hash(password);
	}
}
