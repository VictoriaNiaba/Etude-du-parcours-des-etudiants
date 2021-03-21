package fr.univamu.epu.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.univamu.epu.dao.RegistrationDao;
import fr.univamu.epu.dao.RegistrationYearInfoDao;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationYearInfo;
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

	@Captor
	ArgumentCaptor<Collection<RegistrationYearInfo>> registrationYearInfoCaptor;

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

	@Test
	void givenRegistrations_whenUpload_thenPersistRegistrationYearInfos() {
		// given
		InputStream inputStream = mock(InputStream.class);
		Set<Registration> registrations = Set.of(
				new Registration("SIC2AA", "zereh", 2018),
				new Registration("SIN2AA", "afzfa", 2018),
				new Registration("SIN3AA", "xqzdz", 2018),
				new Registration("SIC3AA", "vrzef", 2019));

		List<RegistrationYearInfo> expectedRegistrationYearInfos = List.of(
				new RegistrationYearInfo(2018, 3, new Date()),
				new RegistrationYearInfo(2019, 1, new Date()));

		when(registrationCsvParser.parse(inputStream)).thenReturn(registrations);

		// when
		registrationManager.upload(inputStream);

		// then
		verify(registrationCsvParser, times(1)).parse(inputStream);
		verify(regYearInfoDao, times(1)).addAll(registrationYearInfoCaptor.capture());

		List<RegistrationYearInfo> actuals = new ArrayList<>(registrationYearInfoCaptor.getValue());
		Collections.sort(actuals, Comparator.comparing(RegistrationYearInfo::getYear));

		for (int i = 0; i < actuals.size(); i++) {
			RegistrationYearInfo actual = actuals.get(i);
			RegistrationYearInfo expected = expectedRegistrationYearInfos.get(i);
			
			assertThat(actual.getYear()).isEqualTo(expected.getYear());
			assertThat(actual.getRegistrationCount()).isEqualTo(expected.getRegistrationCount());
			assertThat(actual.getTimeStamp()).isCloseTo(expected.getTimeStamp(),
					within(100, ChronoUnit.MILLIS).getValue());

		}
	}

	@Test
	void givenOldRegistrations_whenUpload_thenRemoveOldRegistrationYearInfos() {
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
		verify(regYearInfoDao, times(1)).deleteAllByYear(2018);
		verify(regYearInfoDao, times(1)).deleteAllByYear(2019);
	}
}
