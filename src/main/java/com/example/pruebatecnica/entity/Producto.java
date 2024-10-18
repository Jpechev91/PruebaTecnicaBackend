package com.example.pruebatecnica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "producto_nexsys")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "p_id")
	private Long idProducto;

	@Column(name = "name")
	private String name;

	@Column(name = "buy_price")
	private Long priceFinal;

	@Column(name = "category_platzi")
	private Long categoryPlatzi;

	@Column(name = "image")
	private String image;

}
