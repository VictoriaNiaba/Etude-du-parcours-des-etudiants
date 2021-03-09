package fr.univamu.epu.services.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.path.Path;
import fr.univamu.epu.model.registration.Registration;

@Service("pathBuilder")
public class PathBuilder {

	@Autowired
	Dao<Registration> registrationDao;
	@Autowired
	Dao<Path> pathDao;

	private Map<String, Integer> stepCount = new HashMap<String, Integer>();

	// TODO: a séparer en plusieurs methodes pour que ça soit + lisible
	public void buildPaths() {

		List<Registration> regs = new ArrayList<Registration>(registrationDao.findAll(Registration.class));
		Collections.sort(regs, new Comparator<Registration>() {
			public int compare(Registration o1, Registration o2) {
				return o1.getStudentCode().compareTo(o2.getStudentCode());
			}
		});

		// StudentPath
		List<List<Registration>> studentPaths = new ArrayList<List<Registration>>();

		// FILTRE DES ETAPES A ENLEVER
		List<String> badSteps = new ArrayList<>();
		// badSteps.add("SC2IN1");

		// gen des students paths
		String currentStudentCode = "";
		List<Registration> currentStudentRegs = new ArrayList<Registration>();
		for (Registration reg : regs) {
			if (!currentStudentCode.equals(reg.getStudentCode())) {
				if (!currentStudentRegs.isEmpty()) {// changement de student code, on a toutes nos ia du student courant
					studentPaths.add(generateStudentPathFromRegs(currentStudentRegs));
					currentStudentRegs = new ArrayList<Registration>();
				}
				currentStudentCode = reg.getStudentCode();
			}
			currentStudentRegs.add(reg);
		}

		// gen des paths spécifique dans une map
		Map<List<String>, List<Integer>> pathmap = new HashMap<List<String>, List<Integer>>();

		for (List<Registration> sp : studentPaths) {
			List<String> stepCodes = new ArrayList<String>();
			List<Integer> countAndYears = new ArrayList<Integer>(2);

			for (Registration reg : sp) {
				if (badSteps.contains(reg.getStepCode()))
					continue;
				stepCodes.add(reg.getStepCode());
			}
			if (stepCodes.isEmpty())
				continue;
			if (!pathmap.containsKey(stepCodes)) {
				countAndYears.add(1);
				countAndYears.add(sp.get(0).getYear()); // on met l'année du début du chemin pour compter le nombre d'année differentes
			} else {
				countAndYears = pathmap.get(stepCodes);
				countAndYears.set(0, countAndYears.get(0).intValue() + 1);
				for (int i = 1; i < countAndYears.size(); ++i) {
					if (countAndYears.get(i).equals(sp.get(0).getYear())) {
						break;
					} else if (i == countAndYears.size() - 1) {
						countAndYears.add(sp.get(0).getYear());
					}
				}
			}
			pathmap.put(stepCodes, countAndYears);
		}

		// creations des paths a partir de la map
		List<Path> paths = new ArrayList<Path>();
		for (Entry<List<String>, List<Integer>> pathEntry : pathmap.entrySet()) {
			//if (pathEntry.getKey().size() > 2)
			paths.add(new Path(pathEntry.getKey(), pathEntry.getValue().get(0) / (pathEntry.getValue().size() - 1)));
		}
		
		//ajout des paths dans le DAO
		pathDao.addAll(paths);

		//
		paths = new ArrayList<Path>(pathDao.findAll(Path.class));
		Collections.sort(paths, new Comparator<Path>() {
			public int compare(Path o1, Path o2) {
				return o2.getAvgStudentCountPerYear().compareTo(o1.getAvgStudentCountPerYear());
			}
		});

		System.out.println( paths.size() + " paths built by the registration manager init");
		/*
		for (Path p : paths.subList(0, 20))
			System.out.println(p);*/

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
