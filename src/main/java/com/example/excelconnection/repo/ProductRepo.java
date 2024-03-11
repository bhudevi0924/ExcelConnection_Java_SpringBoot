package com.example.excelconnection.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.excelconnection.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
	 Optional<Product> findByProductId(int i);
}
