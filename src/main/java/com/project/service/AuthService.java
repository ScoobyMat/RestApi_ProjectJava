package com.project.service;

import com.project.auth.Credentials;
import com.project.auth.Tokens;
import com.project.model.Student;

public interface AuthService {
	void register(Student student);
	Tokens authenticate(Credentials credentials);
	Tokens refreshTokens(Tokens tokens);
}
