package fr.univamu.epu.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.business.StepManager;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.step.Step;

@RestController
@RequestMapping("/steps")
public class StepControllerREST implements StepControllerSpecification {
	@Autowired
	private StepManager stepManager;

	@Override
	@PostMapping
	public ResponseEntity<Step> add(@RequestBody Step step) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stepManager.add(step));
	}

	@Override
	@PutMapping("/{code}")
	public ResponseEntity<Step> update(@RequestBody Step step, @PathVariable String code) {
		return ResponseEntity.ok(stepManager.update(step));
	}

	@Override
	@DeleteMapping("/{code}")
	public ResponseEntity<Void> remove(@PathVariable String code) {
		stepManager.remove(code);
		return ResponseEntity.noContent().build();
	}

	@Override
	@GetMapping("/{code}")
	public ResponseEntity<Step> find(@PathVariable String code) {
		return ResponseEntity.ok(stepManager.find(code));
	}

	@Override
	@GetMapping
	public ResponseEntity<Collection<Step>> findAll() {
		return ResponseEntity.ok(stepManager.findAll());
	}

	@Override
	@PostMapping(path = "/_upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestParam("csvfile") MultipartFile csvfile)
			throws IOException {
		// Vérification de la présence d'un fichier
		if (csvfile.getOriginalFilename().isEmpty()) {
			throw new UploadException("Aucun fichier n'a été fourni");
		}

		// Vérification de l'extension du fichier
		if (!isCSVFile(csvfile)) {
			throw new UploadException("Le fichier fourni n'est pas au format CSV");
		}

		stepManager.upload(csvfile.getInputStream());
		return ResponseEntity.ok("Le fichier a été uploadé avec succès");

	}

	private boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		return extension.equals("csv");
	}
}