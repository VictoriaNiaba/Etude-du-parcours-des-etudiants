package fr.univamu.epu.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.univamu.epu.business.PathManager;
import fr.univamu.epu.model.path.MergedPath;
import fr.univamu.epu.model.path.Path;

@CrossOrigin("*")
@RestController
@RequestMapping("/paths")
public class PathControllerREST implements PathControllerSpecification {
	@Autowired
	private PathManager pathManager;

	@Override
	@GetMapping
	public ResponseEntity<Collection<MergedPath>> find(@RequestParam(required = false) String firststep, @RequestParam(required = false) String laststep) {
		return ResponseEntity.ok(pathManager.find(firststep,laststep));
	}
}
