package fr.univamu.epu.path.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univamu.epu.path.model.Path;
import fr.univamu.epu.path.services.PathRepository;
import fr.univamu.epu.path.services.PathGenerator;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "paths", produces = "application/json")
public class PathControllerREST {

	@Autowired
	private PathRepository pathRepository;
	@Autowired
	private PathGenerator pathGenerator;

	@GetMapping(value = "")
	public ResponseEntity<Page<Path>> getPaths(Pageable pageable) {
		Page<Path> paths = pathRepository.findAll(pageable);
		return ResponseEntity.ok().body(paths);
	}

	@PostMapping(value = "_generate")
	public ResponseEntity<String> generatePaths() {
		pathGenerator.generatePaths();
		return ResponseEntity.ok().body("done");
	}
}
