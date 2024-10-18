package com.example.pruebatecnica.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.pruebatecnica.entity.CategoriaResponse;
import com.example.pruebatecnica.entity.Producto;
import com.example.pruebatecnica.entity.ProductoRequest;
import com.example.pruebatecnica.entity.ProductoResponse;
import com.example.pruebatecnica.repository.ProductoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductoServiceImpl {

	@Value("${api.products.url}")
	private String productsApiUrl;

	@Value("${api.categories.url}")
	private String categoriesApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductoRepository productoRepository;
	
	public List<ProductoResponse> getAllProducts() {
	    try {
	        ProductoResponse[] products = restTemplate.getForObject(productsApiUrl, ProductoResponse[].class);
	        if (products == null) {
	            throw new RuntimeException("No se obtuvieron datos de la API");
	        }
	        return Arrays.asList(products);
	        
	    } catch (RestClientException e) {
	        // Maneja errores específicos de RestTemplate (problemas de conexión, timeout, etc)
	        throw new RuntimeException("Error al comunicarse con el servicio de productos: " + e.getMessage(), e);
	        
	    } catch (Exception e) {
	        // Maneja cualquier otra excepción inesperada
	        throw new RuntimeException("Error inesperado al obtener productos: " + e.getMessage(), e);
	    }
	}
	
	public List<CategoriaResponse> getAllCategories() {
	    try {
	        CategoriaResponse[] categorias = restTemplate.getForObject(categoriesApiUrl, CategoriaResponse[].class);
	        if (categorias == null) {
	            throw new RuntimeException("No se obtuvieron datos de categorías");
	        }
	        return Arrays.asList(categorias);
	        
	    } catch (RestClientException e) {
	        // Maneja errores específicos de RestTemplate
	        throw new RuntimeException("Error al comunicarse con el servicio de categorías: " + e.getMessage(), e);
	        
	    } catch (Exception e) {
	        // Maneja cualquier otra excepción inesperada
	        throw new RuntimeException("Error inesperado al obtener categorías: " + e.getMessage(), e);
	    }
	}
	
	public ResponseEntity<Long> createProduct(ProductoRequest productoRequest) {
	    try {
	        // Define los encabezados
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Prepara la solicitud
	        HttpEntity<ProductoRequest> requestEntity = new HttpEntity<>(productoRequest, headers);

	        // Envía la solicitud POST
	        ResponseEntity<String> response = restTemplate.exchange(
	            productsApiUrl, 
	            HttpMethod.POST, 
	            requestEntity,
	            String.class
	        );

	        // Procesa la respuesta
	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            // Parsea la respuesta JSON
	            JsonNode root = objectMapper.readTree(response.getBody());
	            Long productId = root.path("id").asLong();

	            try {
	                // Guarda en base de datos local
	                String firstImage = productoRequest.getImages().isEmpty() ? 
	                    "default-image-url" : productoRequest.getImages().get(0);

	                Producto producto = Producto.builder()
	                    .name(productoRequest.getName())
	                    .priceFinal((long) productoRequest.getPriceFinal())
	                    .categoryPlatzi((long) productoRequest.getCategory())
	                    .image(firstImage)
	                    .build();

	                productoRepository.save(producto);
	                
	                return ResponseEntity.status(HttpStatus.CREATED).body(productId);
	            } catch (Exception e) {
	                // Error al guardar en base de datos local
	                throw new RuntimeException("Error al guardar en base de datos local: " + e.getMessage(), e);
	            }
	        } else {
	            throw new RuntimeException("Respuesta inválida del servidor");
	        }

	    } catch (HttpClientErrorException e) {
	        // Errores 4xx
	        throw new RuntimeException("Error en la solicitud: " + e.getMessage(), e);
	        
	    } catch (HttpServerErrorException e) {
	        // Errores 5xx
	        throw new RuntimeException("Error en el servidor externo: " + e.getMessage(), e);
	        
	    } catch (JsonProcessingException e) {
	        // Error al procesar JSON
	        throw new RuntimeException("Error al procesar la respuesta JSON: " + e.getMessage(), e);
	        
	    } catch (ResourceAccessException e) {
	        // Error de conexión
	        throw new RuntimeException("Error de conexión con el servicio externo: " + e.getMessage(), e);
	        
	    } catch (Exception e) {
	        // Cualquier otra excepción
	        throw new RuntimeException("Error inesperado al crear el producto: " + e.getMessage(), e);
	    }
	}
}
