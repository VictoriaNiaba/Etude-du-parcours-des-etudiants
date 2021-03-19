package fr.univamu.epu.business;

import fr.univamu.epu.errorhandler.EmailExistsException;
import fr.univamu.epu.model.user.User;

public interface UserManager {
	User registerNewUserAccount(User account) throws EmailExistsException;
}
