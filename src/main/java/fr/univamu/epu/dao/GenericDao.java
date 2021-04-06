package fr.univamu.epu.dao;

import java.util.Collection;

public interface GenericDao<T, I> {

	public T add(T entity);

	public void addAll(Collection<T> entities);
	
	public void saveAll(Collection<T> entities);

	public T update(T entity);

	public void remove(I id);

	public T find(I id);

	public Collection<T> findAll();

	public void setClazz(Class<T> clazz);

}
