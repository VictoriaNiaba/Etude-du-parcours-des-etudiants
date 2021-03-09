package fr.univamu.epu.controllers;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import fr.univamu.epu.model.path.Path;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestion des cheminements")
public interface PathControllerSpecification {

	@Operation(summary = "Récupère tous les cheminements", //
			description = "", //
			tags = { "path" })
	public ResponseEntity<Collection<Path>> findAll();
}
