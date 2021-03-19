package fr.univamu.epu.dao;

import java.util.Collection;

import fr.univamu.epu.model.user.User;

public interface IUserDao {

	User add(User entity);

	void addAll(Collection<User> entities);

	User update(User entity);

	void remove(Long id);

	User find(Long id);

	User findByEmail(String email);

	Collection<User> findAll();

}
