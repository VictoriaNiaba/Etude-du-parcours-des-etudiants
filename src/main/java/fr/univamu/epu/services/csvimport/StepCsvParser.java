package fr.univamu.epu.services.csvimport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import fr.univamu.epu.model.step.Step;

public class StepCsvParser implements CsvParser {

	private Set<Step> steps = new HashSet<>();	
	
	@Override
	public void parse(File file) {
		try (Scanner scanner = new Scanner(file);) {
		    while (scanner.hasNextLine()) {
		        steps.add(getStepFromLine(scanner.nextLine()));
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private Step getStepFromLine(String line) {
	    Step step = new Step();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(";");
	        if(rowScanner.hasNext()) {
	        	step.setStepCode(rowScanner.next().replace(',',' ').replace('"',' ').trim());
	        	if(rowScanner.hasNext())
	        		step.setName(rowScanner.next().replace(',',' ').replace('"',' ').trim());
	        }
	    }
	    return step;
	}

	public Set<Step> getSteps() {
		return steps;
	}

}
