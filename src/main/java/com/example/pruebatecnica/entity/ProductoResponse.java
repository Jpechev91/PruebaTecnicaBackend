package com.example.pruebatecnica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductoResponse {

	@JsonProperty("id")
	private Long pid;

	@JsonProperty("title")
	private String name;

	@JsonProperty("price")
	private int priceFinal;

	@JsonProperty("description")
	private String description;
}