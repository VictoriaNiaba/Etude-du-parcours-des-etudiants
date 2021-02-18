package fr.univamu.epu.path.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import fr.univamu.epu.path.model.Path;
import fr.univamu.epu.path.model.PreBuiltPath;
import fr.univamu.epu.step.model.Step;
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

	@Transactional
	public void generatePaths() {
		StopWatch stopWatch = new StopWatch("Path generation");

		stopWatch.start("fetch pre-built paths from view");
		List<PreBuiltPath> preBuiltPaths = preBuiltPathRepository.findAll();
		stopWatch.stop();

		stopWatch.start("build new paths");
		List<Path> paths = buildPaths(preBuiltPaths);
		stopWatch.stop();

		stopWatch.start("delete old paths");
		pathRepository.deleteAll();
		stopWatch.stop();

		stopWatch.start("save new paths");
		pathRepository.saveAll(paths);
		stopWatch.stop();

		log.info(stopWatch.prettyPrint());
	}

	private List<Path> buildPaths(List<PreBuiltPath> preBuiltpaths) {
		List<Path> paths = new ArrayList<>();
		preBuiltpaths.forEach(preBuiltPath -> {
			// Compute RealPaths <-> Steps relations
			String[] stepCodes = preBuiltPath.getStepSequence().split(",");
			List<Step> steps = new ArrayList<>();
			for (String stepCode : stepCodes) {
				steps.add(stepRepository.getOne(stepCode));
			}

			// Compute paths
			Path path = Path.builder()
					.code(preBuiltPath.getCode())
					.avgStudentCountPerYear(preBuiltPath.getAvgStudentCountPerYear())
					.steps(steps)
					.build();

			paths.add(path);
		});
		return paths;
	}
}
