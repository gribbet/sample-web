package server.service;

import java.util.List;

import server.domain.DomainObject;

public abstract interface AbstractDomainService<Id, T extends DomainObject<Id>> {
	public void create(T o);

	public T find(Id id);

	public T find(T o);

	public T update(T o);

	public T modify(T o);

	public void delete(T o);

	public void delete(Id id);

	public List<T> list(Integer start, Integer count);

	public List<T> list();

	public int count();

	public void validate(T o);
}
