package fr.univamu.epu.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GenericDao<T> implements Dao<T> {

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	EntityManager em;

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
	public void remove(Class<T> clazz, Object id) {
		T entity = em.find(clazz, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	@Override
	public T find(Class<T> clazz, Object id) {
		return em.find(clazz, id);
	}

	@Override
	public Collection<T> findAll(Class<T> clazz) {
		CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
		
		criteriaQuery.select(criteriaQuery.from(clazz));
		TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
}
