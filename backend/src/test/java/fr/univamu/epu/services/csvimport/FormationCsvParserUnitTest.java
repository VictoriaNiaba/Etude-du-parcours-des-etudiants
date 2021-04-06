package fr.univamu.epu.services.csvimport;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;

import fr.univamu.epu.model.formation.Formation;

class FormationCsvParserUnitTest {

	private FormationCsvParser formationCsvParser = new FormationCsvParser();

	private Comparator<Formation> formationComparator = Comparator
			.comparing(Formation::getFormation_code)
			.thenComparing(Formation::getFormation_name)
			.thenComparing(Formation::getType)
			.thenComparing(Formation::getUrl)
			.thenComparing(Formation::getDescription);

	@Test
	void givenFormationsAsCsv_whenParse_thenReturnFormationsAsObjects() {
		// given
		Collection<Formation> expected = Arrays.asList(
				new Formation("MEPSIM-PRSIMPAA",
						"Parcours type : Métrologie industrielle (MI)",
						"La mention Métiers de l’Instrumentation, de la Mesure et du Contrôle qualité (MIMC)...",
						"LP",
						"http://formations.univ-amu.fr/MEPSIM-PRSIMPAA.html",
						new HashSet<>()),
				new Formation("ME5HSM-PRSSM5AA",
						"Parcours type : Ingénierie et conception sonore",
						"Le parcours « Ingénierie et conception sonore » dispense une formation de haut niveau...",
						"master",
						"http://formations.univ-amu.fr/ME5HSM-PRSSM5AA.html",
						new HashSet<>()));

		TextStringBuilder fileContentBuilder = new TextStringBuilder();

		expected.forEach(formation -> {
			List<String> values = Arrays.asList(
					formation.getFormation_code(),
					formation.getType(),
					formation.getFormation_name(),
					formation.getUrl(),
					formation.getDescription());
			fileContentBuilder.appendWithSeparators(values, "\t").appendln("");
		});

		InputStream inputStream = new ByteArrayInputStream(fileContentBuilder.build().getBytes());

		// when
		Set<Formation> actual = formationCsvParser.parse(inputStream);

		// then
		assertThat(actual).usingElementComparator(formationComparator)
				.containsExactlyInAnyOrderElementsOf(expected);
	}
	
	@Test
	void givenFormationsAsCsv_whenParse_thenIgnoreHeader() {
		// given
		Collection<Formation> expected = Arrays.asList(
				new Formation("MEPSIM-PRSIMPAA",
						"Parcours type : Métrologie industrielle (MI)",
						"La mention Métiers de l’Instrumentation, de la Mesure et du Contrôle qualité (MIMC)...",
						"LP",
						"http://formations.univ-amu.fr/MEPSIM-PRSIMPAA.html",
						new HashSet<>()));

		String header = "CODE\tTYPE\tNOM\tURL.\tDESC.";
		TextStringBuilder fileContentBuilder = new TextStringBuilder()
				.appendln(header);

		expected.forEach(formation -> {
			List<String> values = Arrays.asList(
					formation.getFormation_code(),
					formation.getType(),
					formation.getFormation_name(),
					formation.getUrl(),
					formation.getDescription());
			fileContentBuilder.appendWithSeparators(values, "\t").appendln("");
		});

		InputStream inputStream = new ByteArrayInputStream(fileContentBuilder.build().getBytes());

		// when
		Set<Formation> actual = formationCsvParser.parse(inputStream);

		// then
		assertThat(actual).usingElementComparator(formationComparator)
				.containsExactlyInAnyOrderElementsOf(expected);
	}
}
