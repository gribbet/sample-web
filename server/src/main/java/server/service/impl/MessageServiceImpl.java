package server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.filter.impl.ChainedFilter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;

import server.domain.Message;
import server.domain.Message_;
import server.domain.User;
import server.exception.InvalidParameterException;
import server.exception.MissingParameterException;
import server.logger.InjectLogger;
import server.service.MessageService;
import server.service.UserService;

import com.google.common.collect.Ordering;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class MessageServiceImpl extends AbstractDomainServiceImpl<Integer, Message> implements MessageService {
	@InjectLogger
	private Logger logger;
	@Inject
	private UserService userService;
	private boolean indexed = false;

	public MessageServiceImpl() {
		super(Message.class);
	}

	@Override
	@Transactional
	public void create(Message message) {
		validate(message);

		message.setTime(new Date());

		message.setUser(userService.find(message.getUser()));

		super.create(message);
	}

	@Override
	public List<Message> list(User user, SortType sort, boolean reverse, Integer start, Integer count) {
		logger.debug("List Messages " + user);
		if (sort == null)
			sort = SortType.DEFAULT;
		if (start == null)
			start = 0;
		if (count == null || count > 100)
			count = 100;
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<Message> query = builder.createQuery(Message.class);
		Root<Message> root = query.from(Message.class);
		query.select(root);

		query.where(where(builder, root, user));

		Order order = null;
		if (sort.equals(SortType.TIME))
			order = builder.desc(root.get(Message_.time));
		if (order != null) {
			if (reverse)
				order = order.reverse();
			query.orderBy(order);
		}

		TypedQuery<Message> typedQuery = entityManager.get().createQuery(query);
		typedQuery.setHint("org.hibernate.cacheable", true);
		typedQuery.setFirstResult(start);
		typedQuery.setMaxResults(count);
		return typedQuery.getResultList();
	}

	@Override
	public int count(User user) {
		logger.debug("Count Messages");
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Message> root = query.from(Message.class);
		query.select(builder.count(root));

		query.where(where(builder, root, user));

		TypedQuery<Long> typedQuery = entityManager.get().createQuery(query);
		typedQuery.setHint("org.hibernate.cacheable", true);
		return typedQuery.getSingleResult().intValue();
	}

	private Predicate[] where(CriteriaBuilder builder, Root<Message> root, User user) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (user != null)
			predicates.add(builder.equal(root.get(Message_.user), user));

		return predicates.toArray(new Predicate[] {});
	}

	@Override
	public List<Message> list(String query, SortType sort, boolean reverse, Integer start, Integer count) {
		logger.debug("List Message " + query);
		if (sort == null)
			sort = SortType.DEFAULT;
		if (start == null)
			start = 0;
		if (count == null || count > 100)
			count = 100;
		if (StringUtils.isEmpty(query))
			return list((User) null, sort, reverse, start, count);
		else
			return search(query, sort, reverse, start, count);
	}

	@Override
	public int count(String query) {
		return buildQuery(query).getResultSize();
	}

	private List<Message> search(String queryString, SortType sort, boolean reverse, Integer start, Integer count) {
		FullTextQuery query = buildQuery(queryString);
		query.setFirstResult(start);
		query.setMaxResults(count);
		@SuppressWarnings("unchecked")
		List<Message> results = (List<Message>) query.getResultList();

		Ordering<Message> ordering = null;
		if (sort.equals(SortType.TIME))
			ordering = Ordering.from(timeComparator);
		if (ordering != null) {
			if (reverse)
				ordering = ordering.reverse();
			results = ordering.sortedCopy(results);
		}

		return results;
	}

	private FullTextQuery buildQuery(String queryString) {
		String[] fields = new String[] { "user.name", "user.username", "user.email", "subject", "title" };

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
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Message.class);

		ChainedFilter filter = new ChainedFilter();
		if (!filter.isEmpty())
			fullTextQuery.setFilter(filter);

		return fullTextQuery;
	}

	private synchronized FullTextEntityManager getFullTextEntityManager() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager.get());
		if (!indexed) {
			try {
				fullTextEntityManager.createIndexer(Message.class).startAndWait();
			} catch (InterruptedException e) {
				logger.error("Indexing failed", e);
			}
			indexed = true;
		}
		return fullTextEntityManager;
	}

	public void validate(Message message) {
		if (message.getUser() == null || message.getUser().getId() == null)
			throw new MissingParameterException("user");
		User user = userService.find(message.getUser());
		if (user == null)
			throw new InvalidParameterException("user", "User not found");
	}

	private final Comparator<Message> timeComparator = new Comparator<Message>() {
		@Override
		public int compare(Message message1, Message message2) {
			return message1.getTime().compareTo(message2.getTime());
		}
	};
}
