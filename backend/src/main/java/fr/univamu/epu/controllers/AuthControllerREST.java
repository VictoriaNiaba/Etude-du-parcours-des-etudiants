package fr.univamu.epu.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerREST implements AuthControllerSpecification {
	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}
