package fr.univamu.epu.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.user.User;

@Repository
@Transactional
public class UserDao extends GenericDao<User, Long> implements IUserDao {

	UserDao() {
		setClazz(User.class);
	}

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
