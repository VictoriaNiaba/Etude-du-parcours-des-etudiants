package fr.univamu.epu.business;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.dao.UserDao;
import fr.univamu.epu.errorhandler.EmailExistsException;
import fr.univamu.epu.model.user.User;

@Service
public class UserInMemoryManager implements UserManager {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Dao<User> dao;

	@Autowired
	private UserDao udao;

	@PostConstruct
	public void init() throws EmailExistsException {
		registerNewUserAccount(new User("niaba.victoria@gmail.com", "password"));
		registerNewUserAccount(new User("admin.email@email.email", "psw"));
	}

	@Override
	public User registerNewUserAccount(User account) throws EmailExistsException {
		if (emailExist(account.getEmail())) {
			throw new EmailExistsException(
					"There is an account with that email adress:" + account.getEmail());
		}
		account.setPassword(passwordEncoder.encode(account.getPassword()));

		return dao.add(account);
	}

	private boolean emailExist(String email) {
		return udao.findByEmail(email) != null;
	}

}
