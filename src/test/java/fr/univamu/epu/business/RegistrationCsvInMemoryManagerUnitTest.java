package fr.univamu.epu.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.univamu.epu.dao.RegistrationDao;
import fr.univamu.epu.dao.RegistrationYearInfoDao;
import fr.univamu.epu.model.path.Path;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.services.csvimport.RegistrationCsvParser;
import fr.univamu.epu.services.path.PathBuilder;

@ExtendWith(MockitoExtension.class)
class RegistrationCsvInMemoryManagerUnitTest {

	@Mock
	private RegistrationYearInfoDao regYearInfoDao;
	@Mock
	private RegistrationDao registrationDao;
	@Mock
	private RegistrationCsvParser registrationCsvParser;
	@Mock
	private PathBuilder pathBuilder;

	@InjectMocks
	private RegistrationCsvInMemoryManager registrationManager;

	@Test
	void givenInputStream_whenUpload_thenDelegateToCsvParserAndDao() {
		// given
		InputStream inputStream = mock(InputStream.class);
		Set<Registration> expected = Set.of();
		when(registrationCsvParser.parse(inputStream)).thenReturn(expected);

		// when
		registrationManager.upload(inputStream);

		// then
		verify(registrationDao, times(1)).addAll(expected);
		verify(registrationCsvParser, times(1)).parse(inputStream);
	}

	@Test
	void givenOldRegistrations_whenUpload_thenReplaceByNewRegistrations() {
		// given
		InputStream inputStream = mock(InputStream.class);
		Set<Registration> expected = Set.of(
				new Registration("SIC2AA", "zereh", 2018),
				new Registration("SIC3AA", "vrzef", 2019));
		when(registrationCsvParser.parse(inputStream)).thenReturn(expected);

		// when
		registrationManager.upload(inputStream);

		// then
		verify(registrationCsvParser, times(1)).parse(inputStream);
		verify(registrationDao, times(1)).addAll(expected);
		verify(registrationDao, times(1)).deleteAllByYear(2018);
		verify(registrationDao, times(1)).deleteAllByYear(2019);
	}
}
