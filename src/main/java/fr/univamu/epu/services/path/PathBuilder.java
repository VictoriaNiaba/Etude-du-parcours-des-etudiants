package fr.univamu.epu.services.path;

import java.util.ArrayList;
import java.util.Collection;
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
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.model.step.StepStat;
import fr.univamu.epu.services.step.StepFormationLinker;

@Service("pathBuilder")
public class PathBuilder {

	@Autowired
	Dao<Registration> registrationDao;
	@Autowired
	Dao<Path> pathDao;
	@Autowired
	Dao<Step> stepDao;
	@Autowired
	Dao<StepStat> stepStatDao;
	@Autowired
	StepFormationLinker sfl;

	public void buildPaths() {
		// delete old paths
		deleteOldPaths();

		// getAllRegs & sort by student Code
		List<Registration> regs = new ArrayList<Registration>(registrationDao.findAll(Registration.class));
		Collections.sort(regs, new Comparator<Registration>() {
			public int compare(Registration o1, Registration o2) {
				return o1.getStudentCode().compareTo(o2.getStudentCode());
			}
		});

		// getBadSteps du step formation linker qui lie les formations et les etapes,
		// genere la liste des étapes a ignorer
		List<String> badSteps = sfl.getBadSteps();

		// gen des students paths
		List<List<Registration>> studentPaths = generateStudentPaths(regs, badSteps);

		//delete old stepstats
		if(!stepStatDao.findAll(StepStat.class).isEmpty()) {
			for(StepStat s : stepStatDao.findAll(StepStat.class)) {
				stepStatDao.remove(StepStat.class, s.getStepStatId());
			}
		}	
		// generate Step Stats (depends on student paths)
		generateStepStats(studentPaths, badSteps);

		// gen des paths spécifique dans une map
		Map<List<String>, List<Integer>> pathmap = generatePathMap(studentPaths);

		// creations des paths a partir de la map
		List<Path> paths = getPathsFromMap(pathmap);

		// ajout des paths dans le DAO
		pathDao.addAll(paths);

		System.out.println(pathDao.findAll(Path.class).size() + " paths built by the registration manager init");
	}

	private List<Path> getPathsFromMap(Map<List<String>, List<Integer>> pathmap) {
		List<Path> paths = new ArrayList<Path>();
		for (Entry<List<String>, List<Integer>> pathEntry : pathmap.entrySet()) {
			paths.add(new Path(pathEntry.getKey(),
					(double) (pathEntry.getValue().get(0) / (pathEntry.getValue().size() - 1))));
		}
		return paths;
	}

	private void deleteOldPaths() {
		Collection<Path> oldPaths = pathDao.findAll(Path.class);
		if (!oldPaths.isEmpty()) {
			for (Path p : oldPaths)
				pathDao.remove(Path.class, p.pathId);
		}
	}

	private List<List<Registration>> generateStudentPaths(List<Registration> regs, List<String> badSteps) {
		List<List<Registration>> studentPaths = new ArrayList<List<Registration>>();
		String currentStudentCode = "";
		List<Registration> currentStudentRegs = new ArrayList<Registration>();
		for (Registration reg : regs) {
			if (badSteps.contains(reg.getStepCode()))
				continue;
			if (!currentStudentCode.equals(reg.getStudentCode())) {
				if (!currentStudentRegs.isEmpty()) {// changement de student code, on a toutes nos ia du student courant

					Collections.sort(currentStudentRegs, new Comparator<Registration>() {
						public int compare(Registration o1, Registration o2) {
							return o1.getYear().compareTo(o2.getYear());
						}
					});
					studentPaths.add(currentStudentRegs);
					currentStudentRegs = new ArrayList<Registration>();
				}
				currentStudentCode = reg.getStudentCode();
			}
			currentStudentRegs.add(reg);
		}
		return studentPaths;
	}

	public void generateStepStats(List<List<Registration>> studentPaths, List<String> badSteps) {
		Collection<Step> steps = stepDao.findAll(Step.class);
		for (Step s : steps) {
			if (badSteps.contains(s.getStep_code()))
				continue;
			Map<String, List<Integer>> mapStepsIn = new HashMap<String, List<Integer>>();
			Map<String, List<Integer>> mapStepsOut = new HashMap<String, List<Integer>>();

			for (List<Registration> regs : studentPaths) {
				for (int i = 0; i < regs.size(); i++) {
					if (!regs.get(i).getStepCode().equals(s.getStep_code()))
						continue;
					else {
						// stepsIn
						if (i != 0) {
							List<Integer> l = new ArrayList<Integer>();
							if (mapStepsIn.containsKey(regs.get(i - 1).getStepCode())) {
								l = mapStepsIn.get(regs.get(i - 1).getStepCode());
								l.set(0, l.get(0) + 1);
								if (!l.contains(regs.get(i).getYear())) {
									l.add(regs.get(i).getYear());
								}
							} else {
								l = new ArrayList<Integer>();
								l.add(1);
								l.add(regs.get(i).getYear());
							}
							mapStepsIn.put(regs.get(i - 1).getStepCode(), l);
						}
						// stepsOut
						if (i != regs.size() - 1) {
							List<Integer> l = new ArrayList<Integer>();
							if (mapStepsOut.containsKey(regs.get(i + 1).getStepCode())) {
								l = mapStepsOut.get(regs.get(i + 1).getStepCode());
								l.set(0, l.get(0) + 1);
								if (!l.contains(regs.get(i).getYear())) {
									l.add(regs.get(i).getYear());
								}
							} else {
								l = new ArrayList<Integer>();
								l.add(1);
								l.add(regs.get(i).getYear());
							}
							mapStepsOut.put(regs.get(i + 1).getStepCode(), l);
						}
						break;
					}
				}
			}
			List<StepStat> stepsIn = new ArrayList<StepStat>();
			for (Entry<String, List<Integer>> entryIn : mapStepsIn.entrySet()) {
				stepsIn.add(new StepStat(entryIn.getKey(),
						(double) (entryIn.getValue().get(0) / (entryIn.getValue().size() - 1)))); // div
			}
			List<StepStat> stepsOut = new ArrayList<StepStat>();
			for (Entry<String, List<Integer>> entryOut : mapStepsOut.entrySet()) {
				if (entryOut.getKey().equals(s.getStep_code())) { // redoublement
					s.setAverage_repeat((double) (entryOut.getValue().get(0) / (entryOut.getValue().size() - 1)));
				} else {
					stepsOut.add(new StepStat(entryOut.getKey(),
							(double) (entryOut.getValue().get(0) / (entryOut.getValue().size() - 1)))); // div
				}
			}

			for (StepStat ss : stepsIn)
				ss.setStepInOf(s);
			for (StepStat ss : stepsOut)
				ss.setStepOutOf(s);

			s.setSteps_in(stepsIn);
			s.setSteps_out(stepsOut);

			stepStatDao.addAll(stepsIn);
			stepStatDao.addAll(stepsOut);
			stepDao.update(s);
		}
	}

	private Map<List<String>, List<Integer>> generatePathMap(List<List<Registration>> studentPaths) {
		Map<List<String>, List<Integer>> pathmap = new HashMap<List<String>, List<Integer>>();
		for (List<Registration> sp : studentPaths) {
			List<String> stepCodes = new ArrayList<String>();
			List<Integer> countAndYears = new ArrayList<Integer>(2);

			for (Registration reg : sp) {
				if (!stepCodes.contains(reg.getStepCode()))
					stepCodes.add(reg.getStepCode());
			}
			if (stepCodes.isEmpty())
				continue;
			if (!pathmap.containsKey(stepCodes)) {
				countAndYears.add(1);
				countAndYears.add(sp.get(0).getYear()); // on met l'année du début du chemin pour compter le nombre
														// d'année differentes
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
		return pathmap;
	}

}
