package fr.univamu.epu.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDao<T, I> implements GenericDao<T, I> {

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	protected EntityManager em;

	private Class<T> clazz;

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T add(T entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public void addAll(Collection<T> entities) {
		int count = 0;
		for (T entity : entities) {
			if (count % 10000 == 0) {
				em.flush();
				em.clear();
			}
			em.persist(entity);
			++count;
		}
	}

	@Override
	public T update(T entity) {
		return em.merge(entity);
	}

	@Override
	public void remove(I id) {
		T entity = em.find(clazz, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	@Override
	public T find(I id) {
		return em.find(clazz, id);
	}

	@Override
	public Collection<T> findAll() {
		CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);

		criteriaQuery.select(criteriaQuery.from(clazz));
		TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	@Override
	public void executeQuery(String query) {
		em.createQuery(query).executeUpdate();
	}

	@Override
	public void executeQueryWithIntParam(String query, Object param) {
		em.createQuery(query).setParameter("p", param).executeUpdate();
	}

}
