package fr.univamu.epu.services.csvimport;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.univamu.epu.model.step.Step;

@Service("stepCsvParser")
public class StepCsvParser implements CsvParser<Step> {

	public StepCsvParser() {
		super();
	}

	@Override
	public Set<Step> parse(InputStream inputStream) {
		Set<Step> steps = new HashSet<>();

		try (Scanner scanner = new Scanner(inputStream)) {
			while (scanner.hasNextLine()) {
				steps.add(getStepFromLine(scanner.nextLine()));
			}
		}

		return steps;
	}

	private Step getStepFromLine(String line) {
		Step step = new Step();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(";");
			if (rowScanner.hasNext()) {
				String s = rowScanner.next();
				if (s.codePointAt(0) == 0xfeff)
					s = s.substring(1, s.length()); // BOM présent dans le fichier IA.csv
				step.setStepCode(s.replace(',', ' ').replace('"', ' ').strip());
				if (rowScanner.hasNext())
					step.setName(rowScanner.next().replace(',', ' ').replace('"', ' ').strip());
			}
		}
		// BOM check System.out.println("SYSTEM POINT OUT PRINT :"+step.getStepCode() +
		// " + "+step.getStepCode().length());
		// System.out.println("la step:"+step);
		return step;
	}

}
