package fr.univamu.epu.services.step;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.univamu.epu.dao.FormationDao;
import fr.univamu.epu.dao.StepDao;
import fr.univamu.epu.model.formation.Formation;
import fr.univamu.epu.model.step.Step;

@ExtendWith(MockitoExtension.class)
class StepFormationLinkerUnitTest {
	@Mock
	private FormationDao formationDao;
	@Mock
	private StepDao stepDao;

	@InjectMocks
	private StepFormationLinker stepFormationLinker;

	@Captor
	ArgumentCaptor<Formation> formationCaptor;

	@Test
	void givenSteps_whenBuildPaths_thenFilterNonScientificSteps() {
		// given
		Set<Step> steps = Set.of(
				new Step("A_FAKE_STEP", ""),
				new Step("B_FAKE_STEP", ""),
				new Step("C_FAKE_STEP", ""),
				new Step("D_FAKE_STEP", ""),
				new Step("E_FAKE_STEP", ""),
				new Step("SIN3AA", ""));

		Collection<Formation> formations = Arrays.asList(
				new Formation("ME3SIN-PRSIN3AA", "", "", "", "", new HashSet<>()));

		List<String> expected = Arrays.asList("A_FAKE_STEP",
				"B_FAKE_STEP", "C_FAKE_STEP", "D_FAKE_STEP",
				"E_FAKE_STEP");

		when(formationDao.findAll()).thenReturn(formations);

		// when
		stepFormationLinker.linkAndAddAll(steps);

		// then
		verify(formationDao, times(1)).findAll();

		List<String> actual = stepFormationLinker.getBadSteps();
		assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
	}

	@Test
	void givenSteps_whenBuildPaths_thenFilterOrphanSteps() {
		// given
		Set<Step> steps = Set.of(
				new Step("SIN3AB", ""),
				new Step("SIN3AA", ""));

		Collection<Formation> formations = Arrays.asList(
				new Formation("ME3SIN-PRSIN3AA", "", "", "", "", new HashSet<>()));

		List<String> expected = Arrays.asList("SIN3AB");

		when(formationDao.findAll()).thenReturn(formations);

		// when
		stepFormationLinker.linkAndAddAll(steps);

		// then
		verify(formationDao, times(1)).findAll();

		List<String> actual = stepFormationLinker.getBadSteps();
		assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
	}

}
