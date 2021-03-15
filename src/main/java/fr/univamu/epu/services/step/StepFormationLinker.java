package fr.univamu.epu.services.step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.formation.Formation;
import fr.univamu.epu.model.step.Step;

@Service("formationStepLinker")
public class StepFormationLinker {

	@Autowired
	Dao<Formation> formationDao;

	private List<String> badSteps = new ArrayList<String>();

	public Set<Step> link(Set<Step> steps) {
		List<Step> stepList = new ArrayList<Step>(steps);
		Collections.sort(stepList, new Comparator<Step>() {
			public int compare(Step o1, Step o2) {
				return o1.getStep_code().compareTo(o2.getStep_code());
			}
		});
		List<Formation> formationList = new ArrayList<Formation>(formationDao.findAll(Formation.class));
		Collections.sort(formationList, new Comparator<Formation>() {
			public int compare(Formation o1, Formation o2) {
				return o1.getFormation_code().compareTo(o2.getFormation_code());
			}
		});

		Set<Step> resultSteps = new HashSet<Step>();
		for (Step s : stepList) {
			if (!s.getStep_code().substring(0, 1).equals("S")) {
				badSteps.add(s.getStep_code());
				continue; // enleve tout ce qui n'est pas fac de sciences
			}
			boolean badStep = true;
			for (Formation f : formationList) {
				if (!(f.getFormation_code().substring(9, 12).equals(s.getStep_code().substring(0, 3))
						&& (f.getFormation_code().substring(14, 15).equals(s.getStep_code().substring(5, 6))
								|| s.getStep_code().substring(5).equals("T"))))
					continue;
				else {
					if (s.getStep_code().substring(3, 4).equals("P") && !(f.getType().equals("LP")))
						continue;
					else if (f.getType().equals("licence") && Integer.parseInt(s.getStep_code().substring(3, 4)) > 3)
						continue;
					else if (f.getType().equals("master") && Integer.parseInt(s.getStep_code().substring(3, 4)) <= 3)
						continue;
					f.addStep(s);
					resultSteps.add(s);
					badStep = false;
				}
			}
			if (badStep)
				badSteps.add(s.getStep_code());
		}

		return resultSteps;
	}

	public List<String> getBadSteps() {
		return badSteps;
	}
}
