package fr.univamu.epu.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.univamu.epu.model.user.User;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(UserJpaDao.class)
class UserDaoIntegrationTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
	private UserJpaDao dao;

	@Test
	void givenExistingEmail_whenFindByEmail_thenReturnUser() {
		// given
		User expected = new User("niaba.victoria@gmail.com", "password");
		em.persistAndFlush(expected);

		// when
		User actual = dao.findByEmail(expected.getEmail());

		// then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void givenNoMatchingEmails_whenFindByEmail_thenReturnNull() {
		// given
		String nonMatchingEmail = "nonMatchingEmail";

		// when
		User actual = dao.findByEmail(nonMatchingEmail);

		// then
		assertThat(actual).isNull();
	}
}
