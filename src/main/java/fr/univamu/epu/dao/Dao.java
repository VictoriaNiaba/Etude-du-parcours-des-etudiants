package fr.univamu.epu.dao;

import java.util.Collection;

public interface Dao<T> {
	
	public T add(T entity);
	
	public void addAll(Collection<T> entities);

	public T update(T entity);

	public void remove(Class<T> clazz, Object id);
	
	public T find(Class<T> clazz, Object id);

	public Collection<T> findAll(Class<T> clazz);

	public void executeQuery(String query);

	public void executeQueryWithIntParam(String query, Object param);
	
}
