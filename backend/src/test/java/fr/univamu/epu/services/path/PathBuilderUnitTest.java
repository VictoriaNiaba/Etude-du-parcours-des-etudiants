package fr.univamu.epu.services.path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.model.step.StepStat;
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
	@Captor
	ArgumentCaptor<Step> stepCaptor;

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

	@Test
	@SuppressWarnings("unchecked")
	void givenDuplicatePaths_whenBuildPaths_thenMergePaths() {
		// given
		Collection<Registration> registrations = Arrays.asList(
				new Registration("SIN1AA", "fnkjj", 2018),
				new Registration("SIN2AA", "fnkjj", 2019),
				new Registration("SIN1AA", "omlm", 2018),
				new Registration("SIN2AA", "omlm", 2019),
				new Registration("SIC1AA", "fahus", 2018),
				new Registration("SIC2AA", "fahus", 2019));
		List<String> expected1 = Arrays.asList("SIN1AA", "SIN2AA");
		List<String> expected2 = Arrays.asList("SIC1AA", "SIC2AA");

		when(registrationDao.findAll()).thenReturn(registrations);

		// when
		pathBuilder.buildPaths();

		// then
		verify(registrationDao, times(1)).findAll();
		verify(pathDao, times(1)).addAll(pathListCaptor.capture());

		List<Path> results = pathListCaptor.getValue();
		assertThat(results).hasSize(2);
		assertThat(results).extracting(Path::getStepCodes)
				.containsExactlyInAnyOrder(expected1, expected2);
	}

	@SuppressWarnings("unchecked")
	@Test
	void givenOldPaths_whenBuildPaths_thenReplaceOldPaths() {
		// given
		Collection<Registration> registrations = Arrays.asList(
				new Registration("SIN1AA", "fnkjj", 2018),
				new Registration("SIN2AA", "fnkjj", 2019));

		Path path = new Path(Arrays.asList("SIC1AA", "SIC2AA"), 34.9);
		path.pathId = 1l;
		Collection<Path> paths = Arrays.asList(path);

		List<String> expected = Arrays.asList("SIN1AA", "SIN2AA");

		when(registrationDao.findAll()).thenReturn(registrations);
		when(pathDao.findAll()).thenReturn(paths);

		// when
		pathBuilder.buildPaths();

		// then
		verify(registrationDao, times(1)).findAll();
		verify(pathDao, times(1)).findAll();
		verify(pathDao, times(1)).addAll(pathListCaptor.capture());
		List<Path> results = pathListCaptor.getValue();

		assertThat(results).hasSize(1);
		assertThat(results).extracting(Path::getStepCodes)
				.containsExactlyInAnyOrder(expected);

		verify(pathDao, times(1)).remove(1l);
	}

	@Test
	void givenRegistrations_whenBuildPaths_thenReturnValidTransitions() {
		// given
		Collection<Registration> registrations = Arrays.asList(
				new Registration("SIN1AA", "fjrij", 2018),
				new Registration("SIN2AA", "fjrij", 2019),
				new Registration("SIN3AA", "fjrij", 2020));

		Collection<Step> steps = Arrays.asList(
				new Step("SIN2AA", ""),
				new Step("SIN1AA", ""),
				new Step("SIN3AA", ""));

		List<String> SIN1AA_out = Arrays.asList("SIN2AA");
		List<String> SIN2AA_in = Arrays.asList("SIN1AA");
		List<String> SIN2AA_out = Arrays.asList("SIN3AA");
		List<String> SIN3AA_in = Arrays.asList("SIN2AA");

		when(registrationDao.findAll()).thenReturn(registrations);
		when(stepDao.findAll()).thenReturn(steps);

		// when
		pathBuilder.buildPaths();

		// then
		verify(registrationDao, times(1)).findAll();
		verify(stepDao, times(1)).findAll();
		verify(stepDao, times(3)).update(stepCaptor.capture());

		List<Step> results = stepCaptor.getAllValues();
		assertThat(results).hasSize(3);

		Collections.sort(results, Comparator.comparing(Step::getStep_code));

		assertThat(results.get(0).getStep_code()).isEqualTo("SIN1AA");
		assertThat(results.get(0).getSteps_in()).isEmpty();
		assertThat(results.get(0).getSteps_out()).extracting(StepStat::getStep_code)
				.containsExactlyInAnyOrderElementsOf(SIN1AA_out);

		assertThat(results.get(1).getStep_code()).isEqualTo("SIN2AA");
		assertThat(results.get(1).getSteps_in()).extracting(StepStat::getStep_code)
				.containsExactlyInAnyOrderElementsOf(SIN2AA_in);
		assertThat(results.get(1).getSteps_out()).extracting(StepStat::getStep_code)
				.containsExactlyInAnyOrderElementsOf(SIN2AA_out);

		assertThat(results.get(2).getStep_code()).isEqualTo("SIN3AA");
		assertThat(results.get(2).getSteps_in()).extracting(StepStat::getStep_code)
				.containsExactlyInAnyOrderElementsOf(SIN3AA_in);
		assertThat(results.get(2).getSteps_out()).isEmpty();
	}

	@Test
	void givenRegistrations_whenBuildPaths_thenReturnValidStudentCounts() {
		// given
		Collection<Registration> registrations = Arrays.asList(
				new Registration("SIN1AA", "fjrij", 2017),
				new Registration("SIN2AA", "fjrij", 2018),
				new Registration("SIN1AA", "egezg", 2018),
				new Registration("SIN2AA", "egezg", 2019),
				new Registration("SIN1AA", "gzhgq", 2018),
				new Registration("SIN2AA", "gzhgq", 2019));

		Collection<Step> steps = Arrays.asList(
				new Step("SIN2AA", ""),
				new Step("SIN1AA", ""));

		when(registrationDao.findAll()).thenReturn(registrations);
		when(stepDao.findAll()).thenReturn(steps);

		// when
		pathBuilder.buildPaths();

		// then
		verify(registrationDao, times(1)).findAll();
		verify(stepDao, times(1)).findAll();
		verify(stepDao, times(2)).update(stepCaptor.capture());

		List<Step> results = stepCaptor.getAllValues();
		assertThat(results).hasSize(2);

		Collections.sort(results, Comparator.comparing(Step::getStep_code));

		assertThat(results.get(0).getStep_code()).isEqualTo("SIN1AA");
		assertThat(results.get(0).getSteps_out().get(0))
				.hasFieldOrPropertyWithValue("step_code", "SIN2AA")
				.hasFieldOrPropertyWithValue("number", 1.0);

		assertThat(results.get(1).getStep_code()).isEqualTo("SIN2AA");
		assertThat(results.get(1).getSteps_in().get(0))
				.hasFieldOrPropertyWithValue("step_code", "SIN1AA")
				.hasFieldOrPropertyWithValue("number", 1.0);
	}

}
