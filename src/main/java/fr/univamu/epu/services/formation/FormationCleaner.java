package fr.univamu.epu.services.formation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.univamu.epu.model.formation.Formation;
import fr.univamu.epu.model.registration.Registration;

@Service("formationCleaner")
public class FormationCleaner {

	public Set<Formation> clean(Set<Formation> formations) {

		List<Formation> formationList = new ArrayList<Formation>(formations);
		Collections.sort(formationList, new Comparator<Formation>() {
			public int compare(Formation o1, Formation o2) {
				return o1.getFormation_code().compareTo(o2.getFormation_code());
			}
		});

		List<List<Formation>> groupByCode = new ArrayList<List<Formation>>();

		String currentFormationCode = "";
		List<Formation> currentFormations = new ArrayList<Formation>();
		for (Formation f : formationList) {
			if (f.getType().equals("DE1") || f.getType().equals("DE2"))
				continue;
			if (!currentFormationCode.equals(f.getFormation_code().substring(0, 6))) {
				if (!currentFormations.isEmpty()) {
					groupByCode.add(currentFormations);
					currentFormations = new ArrayList<Formation>();
				}
				currentFormationCode = f.getFormation_code();
			}
			currentFormations.add(f);
		}

		Set<Formation> result = new HashSet<Formation>();
		for (List<Formation> fs : groupByCode) {
			result.addAll(removeMainFormation(fs));
		}
		return result;
	}

	// remove main formation and update formation names
	private Set<Formation> removeMainFormation(List<Formation> fs) {
		Set<Formation> result = new HashSet<Formation>();
		String nameprefix = fs.get(0).getType() + " " + fs.get(0).getFormation_name() + " -";
		nameprefix = nameprefix.substring(0, 1).toUpperCase() + nameprefix.substring(1); // premiere lettre en maj
		for (int i = 1; i < fs.size(); ++i) {
			Formation f = fs.get(i);
			f.setFormation_name(nameprefix + f.getFormation_name().substring(15));
			result.add(f);
		}
		return result;
	}

}
