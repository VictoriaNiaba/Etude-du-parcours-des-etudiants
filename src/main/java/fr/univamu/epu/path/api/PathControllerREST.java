package fr.univamu.epu.path.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univamu.epu.path.model.Path;
import fr.univamu.epu.path.services.PathRepository;
import fr.univamu.epu.path.services.PathGenerator;
import fr.univamu.epu.path.services.PathMapper;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "paths", produces = "application/json")
public class PathControllerREST {

	@Autowired
	private PathRepository pathRepository;
	@Autowired
	private PathGenerator pathGenerator;
	@Autowired
	private PathMapper pathMapper;

	@GetMapping(value = "")
	public ResponseEntity<List<PathDto>> getPaths() {
		List<Path> paths = pathRepository.findAll(Sort.by(Sort.Direction.DESC, "avgStudentCountPerYear"));
		paths = paths.subList(0, 10);
		List<PathDto> pathsDto = new ArrayList<>();
		for(Path path : paths)
			pathsDto.add(pathMapper.pathToDto(path));
		return ResponseEntity.ok().body(pathsDto);
	}

	@PostMapping(value = "_generate")
	public ResponseEntity<String> generatePaths() {
		pathGenerator.generatePaths();
		return ResponseEntity.ok().body("done");
	}
}
