package com.example.pruebatecnica.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pruebatecnica.entity.CategoriaResponse;
import com.example.pruebatecnica.entity.ProductoRequest;
import com.example.pruebatecnica.entity.ProductoResponse;
import com.example.pruebatecnica.service.ProductoServiceImpl;

@RestController
@RequestMapping("/nexsys/v1/")
public class ProductoController {

	@Autowired
	private ProductoServiceImpl productoServiceImpl;

	@GetMapping("products")
	private ResponseEntity<List<ProductoResponse>> getAllProducts() {
		try {
			return ResponseEntity.ok(productoServiceImpl.getAllProducts());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@GetMapping("categories")
	private ResponseEntity<List<CategoriaResponse>> getAllCategories() {
		try {
			return ResponseEntity.ok(productoServiceImpl.getAllCategories());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@PostMapping("products")
	public ResponseEntity<Map<String, Long>> createProduct(@RequestBody ProductoRequest productoRequest) {
		try {
			// Llama al servicio para crear el producto
			ResponseEntity<Long> response = productoServiceImpl.createProduct(productoRequest);

			// Si la respuesta es exitosa, genera el JSON con el campo pid
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				Map<String, Long> responseBody = new HashMap<>();
				responseBody.put("pid", response.getBody());
				return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
