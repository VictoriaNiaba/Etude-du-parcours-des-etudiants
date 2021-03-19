package fr.univamu.epu.dao;

import fr.univamu.epu.model.user.User;

public interface UserDao extends GenericDao<User, Long> {

	User findByEmail(String email);

}
