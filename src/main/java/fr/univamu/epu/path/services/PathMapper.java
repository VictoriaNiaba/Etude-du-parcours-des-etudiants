package fr.univamu.epu.path.services;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import fr.univamu.epu.path.api.PathDto;
import fr.univamu.epu.path.model.Path;

@Service
public class PathMapper {

	/**
	 * Convertit une instance de la classe Path en une instance de la classe
	 * PathDto.
	 * 
	 * @param path le cheminement à convertir
	 * @return un cheminement prêt à être renvoyé via l'API REST
	 */
	public PathDto pathToDto(Path path) {
		List<String> steps = new ArrayList<>();
		List<Integer> registered = new ArrayList<>();
		
		path.getPathSteps().forEach(pathStep -> {
			String stepCode = pathStep.getPrimaryKey().getStepCode();
			steps.add(stepCode);
			registered.add((int) path.getAvgStudentCountPerYear());
		});
		
		return new PathDto(steps,registered);
	}
}
