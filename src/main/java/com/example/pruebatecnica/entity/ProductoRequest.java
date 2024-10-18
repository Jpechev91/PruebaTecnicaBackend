package com.example.pruebatecnica.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductoRequest {

	@JsonProperty("title")
	private String name;

	@JsonProperty("price")
	private int priceFinal;

	@JsonProperty("description")
	private String description;

	@JsonProperty("categoryId")
	private int category;

	@JsonProperty("images")
	private List<String> images;

}
