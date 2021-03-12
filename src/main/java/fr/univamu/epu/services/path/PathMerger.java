package fr.univamu.epu.services.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.path.MergedPath;
import fr.univamu.epu.model.path.Path;

@Service("pathMerger")
public class PathMerger {

	@Autowired
	Dao<Path> pathDao;

	public Collection<MergedPath> getMergedPath(String firststep, String laststep) {
		
		//System.out.println("firststep="+firststep+"|laststep="+laststep);
		
		Collection<Path> allPaths = pathDao.findAll(Path.class);
		
		Map<List<String>, Double> pathmap = new HashMap<List<String>, Double>();
		List<Path> paths = new ArrayList<Path>();
		for (Path p : allPaths) {
			pathmap.put(new ArrayList<String>(p.getStepCodes()), p.getAvgStudentCountPerYear());
			// filters
			if(firststep != null && !Arrays.asList(firststep.split(",")).contains(p.getStepCodes().get(0))) continue;
			if(laststep != null && !Arrays.asList(laststep.split(",")).contains(p.getStepCodes().get(p.getStepCodes().size()-1))) continue;
			paths.add(p);
		}
		
		
		Collections.sort(paths, new Comparator<Path>() {
			public int compare(Path o1, Path o2) {
				return o2.getAvgStudentCountPerYear().compareTo(o1.getAvgStudentCountPerYear());
			}
		});
		
		List<MergedPath> mergedPaths = new ArrayList<MergedPath>();
		for (Path p : paths) {
			if(mergedPaths.size() == 50) break;
			if(p.getStepCodes().size() < 2) continue;
			List<Double> registered = new ArrayList<Double>();
			while(registered.size() < p.getStepCodes().size()) registered.add((double) 0);
			for (int i = (p.getStepCodes().size()-1); i >= 0 ; i--) {
				if(i == (p.getStepCodes().size()-1)){
					registered.set(i,p.getAvgStudentCountPerYear());
				} else {
					ArrayList<String> stepsKey = new ArrayList<String>(p.getStepCodes().subList(0, i + 1));
					if(pathmap.containsKey(stepsKey)) {
						registered.set(i, pathmap.get(stepsKey) + registered.get(i + 1));
					} else {
						registered.set(i, registered.get(i + 1));
					}
				}
			}
			mergedPaths.add(new MergedPath(p.getStepCodes(),registered));
		}

		return mergedPaths;
	}

}
