package server.service.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;

import server.domain.DomainObject;
import server.logger.InjectLogger;
import server.server.util.Retryable;
import server.service.AbstractDomainService;
import server.util.PropertyUtil;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public abstract class AbstractDomainServiceImpl<Id, T extends DomainObject<Id>> implements AbstractDomainService<Id, T> {
	@InjectLogger
	private Logger logger;
	@Inject
	protected Provider<EntityManager> entityManager;
	protected Class<T> domainClass;

	public AbstractDomainServiceImpl(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	@Override
	@Transactional
	public void delete(T o) {
		logger.debug("Delete " + o);
		entityManager.get().remove(o);
	}

	@Override
	@Transactional
	public void delete(Id id) {
		delete(find(id));
	}

	@Override
	public T find(Id id) {
		logger.debug("List " + domainClass.getSimpleName() + " " + id);
		T t = entityManager.get().find(domainClass, id);
		return t;
	}

	@Override
	public T find(T o) {
		return find(o.getId());
	}

	@Override
	@Transactional
	public T update(T o) {
		validate(o);
		logger.debug("Update " + o);
		return entityManager.get().merge(o);
	}

	@Override
	@Transactional
	@Retryable
	public T modify(T modify) {
		T o = find(modify);
		entityManager.get().detach(o);
		PropertyUtil.copyModifiableProperties(o, modify);
		return update(o);
	}

	@Override
	@Transactional
	public void create(T o) {
		validate(o);
		entityManager.get().persist(o);
		logger.debug("Created " + o);
	}

	@Override
	public List<T> list(Integer start, Integer count) {
		logger.debug("List " + domainClass.getSimpleName());
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(domainClass);
		query.select(query.from(domainClass));
		TypedQuery<T> typedQuery = entityManager.get().createQuery(query);
		typedQuery.setHint("org.hibernate.cacheable", true);
		if (start != null)
			typedQuery.setFirstResult(start);
		if (count != null)
			typedQuery.setMaxResults(count);
		return typedQuery.getResultList();
	}

	@Override
	public List<T> list() {
		logger.debug("List " + domainClass.getSimpleName());
		CriteriaBuilder cb = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<T> c = cb.createQuery(domainClass);
		return entityManager.get().createQuery(c.select(c.from(domainClass))).getResultList();
	}

	@Override
	public int count() {
		logger.debug("Count " + domainClass.getSimpleName());
		CriteriaBuilder cb = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<Long> c = cb.createQuery(Long.class);
		return entityManager.get().createQuery(c.select(cb.count(c.from(domainClass)))).getSingleResult().intValue();
	}

	public void validate(T o) {
	}
}
