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

	@Test
	void givenSteps_whenBuildPaths_thenLinkStepsToFormations() {
		// given
		List<Step> steps = Arrays.asList(
				new Step("SCH3AA", "AMU.L3 Chimie : Chimie"),
				new Step("SPO1P1", "AMU.1ere annee Portail 1"),
				new Step("SBG5AA", "AMU.M2 BSG : Biologie struct"),
				new Step("SMSPAA", "AMU.LP Maintenance syst indus"));

		Collection<Formation> formations = Arrays.asList(
				new Formation("ME3SCH-PRSCH3AA", "Parcours type : Chimie", "", "licence", "", new HashSet<>()),
				new Formation("ME3SPO-PRSPO1P1", "Portail René Descartes", "", "portail", "", new HashSet<>()),
				new Formation("ME5SBG-PRSBG5AA", "Parcours type : Biochimie structurale", "", "master", "",
						new HashSet<>()),
				new Formation("MEPSMS-PRSMSPAA", "Parcours type : Maintenance des équipements de production", "", "LP",
						"", new HashSet<>()));
		
		List<String> formationCodes = formations.stream()
				.map(Formation::getFormation_code)
				.collect(Collectors.toList());

		List<String> expected = Arrays.asList();

		when(formationDao.findAll()).thenReturn(formations);

		// when
		stepFormationLinker.linkAndAddAll(new HashSet<>(steps));

		// then
		verify(formationDao, times(1)).findAll();
		verify(formationDao, times(4)).update(formationCaptor.capture());
		List<Formation> results = formationCaptor.getAllValues();
		
		assertThat(results).hasSize(4);
		Collections.sort(results, Comparator.comparing(Formation::getFormation_code));
		for (int i = 0; i < results.size(); i++) {
			assertThat(results.get(i).getFormation_code()).isIn(formationCodes);
			assertThat(results.get(i).getSteps()).extracting(Step::getStep_code)
					.containsExactlyInAnyOrder(steps.get(i).getStep_code());
		}

		List<String> badSteps = stepFormationLinker.getBadSteps();
		assertThat(badSteps).isEmpty();

	}
}
