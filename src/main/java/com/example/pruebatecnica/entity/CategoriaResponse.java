package com.example.pruebatecnica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CategoriaResponse {

	@JsonProperty("id")
	private Long cid;

	@JsonProperty("name")
	private String title;

}
