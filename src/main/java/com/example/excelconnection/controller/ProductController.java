package com.example.excelconnection.controller;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.excelconnection.entity.Product;
import com.example.excelconnection.helper.Helper;
import com.example.excelconnection.service.ProductService;

@RestController
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/product/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
		if(Helper.checkExcelFormat(file)) {
			this.productService.save(file);
			
			return ResponseEntity.ok(Map.of("message","File uploaded successfully"));
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file");
	}
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return this.productService.getAllProducts();
	}
}
