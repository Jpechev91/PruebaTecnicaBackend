package com.example.pruebatecnica.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.pruebatecnica.security.JwtUtil;

@Service
public class AuthServiceImpl {

	@Value("${api.login.url}")
	private String authUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	public Map<String, Object> authenticateAndGetToken(String email, String password) {
		Map<String, String> authRequest = new HashMap<>();
		authRequest.put("email", email);
		authRequest.put("password", password);

		try {
			ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, authRequest, Map.class);

			if (response.getStatusCode() == HttpStatus.CREATED) {
				String token = jwtUtil.generateToken(email);
//				Map<String, Object> responseBody = response.getBody();
				Map<String, Object> responseBody = new HashMap<>();
				responseBody.put("access_token", token);
				responseBody.put("token_type", "Bearer");
				return responseBody;
			}
		} catch (HttpClientErrorException e) {
			System.err.println("Error de autenticación: " + e.getMessage());
		}

		return null;
	}
}
