package fr.univamu.epu.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.UserDao;
import fr.univamu.epu.model.user.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
		if (user == null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException(email);
		}
		System.out.println(user.getEmail());
		return new CustomUserPrincipal(user);
	}
}