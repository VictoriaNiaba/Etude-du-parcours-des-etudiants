package fr.univamu.epu.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.univamu.epu.model.registration.Registration;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(RegistrationJpaDao.class)
class RegistrationDaoIntegrationTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
	private RegistrationJpaDao dao;

	@Test
	void givenOldRegistrations_whenDeleteAllByYear_thenDeleteOnlyMatchingRegistrations() {
		// given
		Collection<Registration> oldRegistrations = Arrays.asList(
				new Registration("SIN1AA", "fjrij", 2018),
				new Registration("SIN3AA", "egzaef", 2018),
				new Registration("SIG3AA", "fzassz", 2019),
				new Registration("SIG2AA", "omioli", 2020));
		
		Collection<Registration> expectedRegistrations = Arrays.asList(
				new Registration("SIG3AA", "fzassz", 2019),
				new Registration("SIG2AA", "omioli", 2020));
		
		oldRegistrations.forEach(em::persist);
		em.flush();

		// when
		dao.deleteAllByYear(2018);

		// then
		expectedRegistrations.forEach(registration -> {
			Registration found = em.find(Registration.class, registration.id);
			assertThat(found).isEqualTo(registration);
		});
	}
	
	@Test
	void givenOldRegistrations_whenCountByYear_thenReturnRegistrationCount() {
		// given
		Collection<Registration> oldRegistrations = Arrays.asList(
				new Registration("SIN1AA", "fjrij", 2018),
				new Registration("SIN3AA", "egzaef", 2018),
				new Registration("SIG3AA", "fzassz", 2019),
				new Registration("SIG2AA", "omioli", 2020));
		long expectedCount = 2;
		
		oldRegistrations.forEach(em::persist);
		em.flush();

		// when
		long actualCount = dao.countByYear(2018);

		// then
		assertThat(actualCount).isEqualTo(expectedCount);
	}
}
