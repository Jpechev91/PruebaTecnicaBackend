package com.example.pruebatecnica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthRequest {

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

}
