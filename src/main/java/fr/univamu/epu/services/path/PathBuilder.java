package fr.univamu.epu.services.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.path.Path;
import fr.univamu.epu.model.registration.Registration;

@Service("pathBuilder")
public class PathBuilder {

	@Autowired
	Dao dao;

	private Map<String, Integer> stepCount = new HashMap<String, Integer>();

	public void buildPaths() {
		System.out.println("construction DES CHEMINEMENTS");

		List<Registration> regs = new ArrayList<Registration>(dao.getAllRegistrations());
		Collections.sort(regs, new Comparator<Registration>() {
			public int compare(Registration o1, Registration o2) {
				return o1.getStudentCode().compareTo(o2.getStudentCode());
			}
		});

		// StudentPath
		List<List<Registration>> studentPaths = new ArrayList<List<Registration>>();

		// FILTRE DES ETAPES A ENLEVER
		List<String> badSteps = new ArrayList<>();
		badSteps.add("SC2IN1");

		// gen des students paths
		String currentStudentCode = "";
		List<Registration> currentStudentRegs = new ArrayList<Registration>();
		for (Registration reg : regs) {
			if (!currentStudentCode.equals(reg.getStudentCode())) {
				if (!currentStudentRegs.isEmpty())	{// changement de student code, on a toutes nos ia du student courant
					studentPaths.add(generateStudentPathFromRegs(currentStudentRegs));
					currentStudentRegs = new ArrayList<Registration>();
				}
				currentStudentCode = reg.getStudentCode();
			}
			currentStudentRegs.add(reg);
		}

		System.out.println("student paths done => gen des paths");

		// gen des paths
		Map<List<String>, Integer> pathmap = new HashMap<List<String>, Integer>();

		for (List<Registration> sp : studentPaths) {
			List<String> stepCodes = new ArrayList<String>();
			for (Registration reg : sp) {
				// Filtre
				if (badSteps.contains(reg.getStepCode()))
					continue;
				//
				stepCodes.add(reg.getStepCode());
			}
			if (stepCodes.isEmpty())
				continue;
			if (!pathmap.containsKey(stepCodes))
				pathmap.put(stepCodes, 1);
			else
				pathmap.put(stepCodes, (pathmap.get(stepCodes).intValue() + 1));
		}

		
		List<Path> paths = new ArrayList<Path>();
		for (Map.Entry<List<String>, Integer> path : pathmap.entrySet()) {
			paths.add(new Path(path.getKey(),path.getValue()));
		}
		Collections.sort(paths, new Comparator<Path>() {
			public int compare(Path o1, Path o2) {
				return o2.getAvgStudentCountPerYear().compareTo(o1.getAvgStudentCountPerYear());
			}
		});
		
		System.out.println(paths.subList(0, 50));
		
	}

	public List<Registration> generateStudentPathFromRegs(List<Registration> regs) {
		Collections.sort(regs, new Comparator<Registration>() {
			public int compare(Registration o1, Registration o2) {
				return o1.getYear().compareTo(o2.getYear());
			}
		});
		// Integer lastYear = 0000;
		for (Registration reg : regs) {
			// if(reg.getYear().equals(lastYear)) analyse des doublons d'inscriptions
			// System.out.println("doublon IA EN 1 annee " + stepCodes +
			// regs.get(0).getStudentCode());
			// lastYear = reg.getYear();
			if (!stepCount.containsKey(reg.getStepCode()))
				stepCount.put(reg.getStepCode(), 1);
			else
				stepCount.put(reg.getStepCode(), (stepCount.get(reg.getStepCode()).intValue() + 1));
		}
		return regs;
	}

}
