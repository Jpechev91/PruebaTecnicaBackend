package com.example.pruebatecnica.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pruebatecnica.entity.AuthRequest;
import com.example.pruebatecnica.service.AuthServiceImpl;

@RestController
@RequestMapping("/api/v1/")
public class AuthController {

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@PostMapping("auth/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
		Map<String, Object> token = authServiceImpl.authenticateAndGetToken(authRequest.getEmail(),
				authRequest.getPassword());

		if (token != null) {
			return ResponseEntity.ok(token); // Devuelve el token si fue exitoso
		} else {
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("message", "Credenciales inválidas");
			return ResponseEntity.status(401).body(responseBody);
		}
	}

}
