package fr.univamu.epu.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.user.User;

@Repository
@Transactional
public class UserDao {
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	public User findByEmail(String email) {

		TypedQuery<User> query = em.createQuery(
				"SELECT u From User u WHERE email = :email", User.class)
				.setParameter("email", email);
		List<User> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}

	}
}
