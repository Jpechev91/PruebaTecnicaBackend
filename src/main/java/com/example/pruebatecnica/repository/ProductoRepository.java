package com.example.pruebatecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pruebatecnica.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
