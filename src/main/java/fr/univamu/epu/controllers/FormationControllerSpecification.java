package fr.univamu.epu.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.model.formation.Formation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestion des formations")
public interface FormationControllerSpecification {

	@Operation(summary = "Ajoute une formation non-existante", //
			description = "", //
			tags = { "formation" })
	public ResponseEntity<Formation> add(@Valid Formation formation);

	@Operation(summary = "Met à jour une formation à partir de son code", //
			description = "", //
			tags = { "formation" })
	public ResponseEntity<Formation> update(@Valid Formation formation, String code);

	@Operation(summary = "Supprime une formation à partir de son code", //
			description = "", //
			tags = { "formation" })
	public ResponseEntity<Void> remove(String code);

	@Operation(summary = "Récupère une formation en fonction de son code", //
			description = "", //
			tags = { "formation" })
	public ResponseEntity<Formation> find(String code);

	@Operation(summary = "Récupère toutes les formations", //
			description = "", //
			tags = { "formation" })
	public ResponseEntity<Collection<Formation>> findAll();

	@Operation(summary = "Upload un lot de formations", //
			description = "Cette route supporte le format CSV uniquement.", //
			tags = { "formation" })
	public ResponseEntity<String> upload(MultipartFile csvfile) throws IOException;
}
