package fr.univamu.epu.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.univamu.epu.model.registration.RegistrationYearInfo;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(RegistrationYearInfoJpaDao.class)
class RegistrationYearInfoJpaDaoIntegrationTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
	private RegistrationYearInfoJpaDao dao;

	@Test
	void givenOldRegistrationsYearInfo_whenDeleteAllByYear_thenDeleteOnlyMatchingRegistrationsYearInfo() {
		// given
		Collection<RegistrationYearInfo> oldRegistrationYearInfos = Arrays.asList(
				new RegistrationYearInfo(2018, 11987, new Date()),
				new RegistrationYearInfo(2019, 11328, new Date()),
				new RegistrationYearInfo(2020, 12443, new Date()));

		Collection<RegistrationYearInfo> expectedRegistrationYearInfos = Arrays.asList(
				new RegistrationYearInfo(2019, 11328, new Date()),
				new RegistrationYearInfo(2020, 12443, new Date()));

		oldRegistrationYearInfos.forEach(em::persist);
		em.flush();

		// when
		dao.deleteAllByYear(2018);

		// then
		expectedRegistrationYearInfos.forEach(registration -> {
			RegistrationYearInfo found = em.find(RegistrationYearInfo.class, registration.getYear());
			assertThat(found).isEqualTo(registration);
		});
	}
}
