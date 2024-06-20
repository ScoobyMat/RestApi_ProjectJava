package com.project.controller;

import com.project.auth.Credentials;
import com.project.auth.Tokens;
import com.project.service.AuthService;
import com.project.validation.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.model.Student;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthService authService;
	private final ValidationService<Student> studentValidator;

	public AuthController(AuthService authService, ValidationService<Student> studentValidator) {
		this.authService = authService;
		this.studentValidator = studentValidator;
	}

	@PostMapping("/login")
	public ResponseEntity<Tokens> login(@RequestBody Credentials credentials) {
		Tokens tokens = authService.authenticate(credentials);
		return ResponseEntity.ok(tokens);
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody Student student) {
		studentValidator.validate(student);
		authService.register(student);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<Tokens> refreshToken(@RequestBody Tokens tokens) {
		Tokens refreshedTokens = authService.refreshTokens(tokens);
		return ResponseEntity.ok(refreshedTokens);
	}
}
