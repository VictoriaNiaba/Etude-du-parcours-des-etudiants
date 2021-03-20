package fr.univamu.epu.services.path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.univamu.epu.dao.PathDao;
import fr.univamu.epu.dao.RegistrationDao;
import fr.univamu.epu.dao.StepDao;
import fr.univamu.epu.dao.StepStatDao;
import fr.univamu.epu.model.path.Path;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.services.step.StepFormationLinker;

@ExtendWith(MockitoExtension.class)
class PathBuilderUnitTest {

	@Mock
	private RegistrationDao registrationDao;
	@Mock
	private PathDao pathDao;
	@Mock
	private StepDao stepDao;
	@Mock
	private StepStatDao stepStatDao;
	@Mock
	private StepFormationLinker stepFormationLinker;

	@InjectMocks
	private PathBuilder pathBuilder;

	@Captor
	ArgumentCaptor<List<Path>> pathListCaptor;

	@Test
	void givenRegistrationsAndBadSteps_whenBuildPaths_thenFilterBadSteps() {
		// given
		Collection<Registration> registrations = Arrays.asList(
				new Registration("SIN1AA", "fjrij", 2018),
				new Registration("C2i", "fjrij", 2019),
				new Registration("SIN2AA", "fjrij", 2020));
		List<String> badSteps = Arrays.asList("C2i");

		List<String> expected = Arrays.asList("SIN1AA", "SIN2AA");

		when(registrationDao.findAll()).thenReturn(registrations);
		when(stepFormationLinker.getBadSteps()).thenReturn(badSteps);

		// when
		pathBuilder.buildPaths();

		// then
		verify(registrationDao, times(1)).findAll();
		verify(stepFormationLinker, times(1)).getBadSteps();
		verify(pathDao, times(1)).addAll(pathListCaptor.capture());

		List<Path> result = pathListCaptor.getValue();
		assertThat(result).hasSize(1);

		assertThat(result.get(0).getStepCodes()).isEqualTo(expected);
	}

}
