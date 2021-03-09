package fr.univamu.epu.services.csvimport;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.univamu.epu.model.formation.Formation;

@Service("formationCsvParser")
public class FormationCsvParser implements CsvParser<Formation> {

	@Override
	public Set<Formation> parse(InputStream inputStream) {
		Set<Formation> formations = new HashSet<>();

		try (Scanner scanner = new Scanner(inputStream)) {
			while (scanner.hasNextLine()) {
				formations.add(getFormationFromLine(scanner.nextLine()));
			}
		}

		return formations;
	}

	private Formation getFormationFromLine(String line) {
		Formation f = new Formation();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter("\t");
			if (rowScanner.hasNext())
				f.setFormation_code(rowScanner.next());
			if (rowScanner.hasNext())
				f.setFormation_name(rowScanner.next());
			if (rowScanner.hasNext())
				f.setUrl(rowScanner.next());
			if (rowScanner.hasNext())
				f.setDescription(rowScanner.next());
		}
		return f;
	}

}
