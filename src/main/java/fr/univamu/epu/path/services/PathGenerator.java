package fr.univamu.epu.path.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import fr.univamu.epu.path.model.Path;
import fr.univamu.epu.path.model.PathStep;
import fr.univamu.epu.path.model.PathStepPK;
import fr.univamu.epu.path.model.PreBuiltPath;
import fr.univamu.epu.step.services.StepRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PathGenerator {
	@Autowired
	private PathRepository pathRepository;
	@Autowired
	private PreBuiltPathRepository preBuiltPathRepository;
	@Autowired
	private StepRepository stepRepository;
	@Autowired
	private PathStepRepository pathStepRepository;

	@Transactional
	public void generatePaths() {
		StopWatch stopWatch = new StopWatch("Path generation");

		stopWatch.start("fetch pre-built paths from view");
		List<PreBuiltPath> preBuiltPaths = preBuiltPathRepository.findAll();
		stopWatch.stop();
		
		stopWatch.start("build new paths");
		List<Path> paths = buildPaths(preBuiltPaths);
		stopWatch.stop();
		
		stopWatch.start("delete old paths and paths <-> steps relations");
		pathStepRepository.deleteAll();
		pathRepository.deleteAll();
		stopWatch.stop();

		stopWatch.start("save new paths");
		pathRepository.saveAll(paths);
		stopWatch.stop();
		
		stopWatch.start("build paths <-> steps relations");
		Set<PathStep> pathSteps = buildPathSteps(preBuiltPaths);
		stopWatch.stop();
		
		stopWatch.start("save new paths <-> steps relations");
		pathStepRepository.saveAll(pathSteps);
		stopWatch.stop();

		log.info(stopWatch.prettyPrint());
	}
	
	private List<Path> buildPaths(List<PreBuiltPath> preBuiltpaths) {
		List<Path> paths = new ArrayList<>();
		
		preBuiltpaths.forEach(preBuiltPath -> {
			Path path = new Path(
					preBuiltPath.getCode(),
					preBuiltPath.getAvgStudentCountPerYear(),
					null);
			paths.add(path);
		});

		return paths;
	}

	private Set<PathStep> buildPathSteps(List<PreBuiltPath> preBuiltpaths) {
		Set<PathStep> pathSteps = new HashSet<>();

		preBuiltpaths.forEach(preBuiltPath -> {
			String[] stepCodes = preBuiltPath.getStepSequence().split(",");
			for (short i = 1; i <= stepCodes.length; i++) {
				PathStepPK pathStepId = new PathStepPK();
				pathStepId.setPathCode(preBuiltPath.getCode());
				pathStepId.setStepCode(stepCodes[i - 1]);
				
				PathStep pathStep = new PathStep();
				pathStep.setPrimaryKey(pathStepId);
				pathStep.setStep(stepRepository.getOne(stepCodes[i - 1]));
				pathStep.setPath(pathRepository.getOne(preBuiltPath.getCode()));
				pathStep.setStepPosition(i);
				
				pathSteps.add(pathStep);
			}
		});

		return pathSteps;
	}
}
