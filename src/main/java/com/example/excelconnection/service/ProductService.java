//package com.example.excelconnection.service;

//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.excelconnection.entity.Product;
//import com.example.excelconnection.helper.Helper;
//import com.example.excelconnection.repo.ProductRepo;
//
//@Service
//public class ProductService {
//	
//	@Autowired
//	private  ProductRepo productRepo;
//	
//	
//	public void save(MultipartFile file) {
//		
//		try {
//			
//			List<Product> products= Helper.convertExcelToListOfProduct(file.getInputStream());
//			this.productRepo.saveAll(products);
//			
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public List<Product> getAllProducts(){
//		return this.productRepo.findAll();
//	}
//
//}


package com.example.excelconnection.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.excelconnection.entity.Product;
import com.example.excelconnection.helper.Helper;
import com.example.excelconnection.repo.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public void save(MultipartFile file) {
        try {
            List<Product> products = Helper.convertExcelToListOfProduct(file.getInputStream());
            Set<Integer> existingProductIds = new HashSet<>();

            for (Product product : productRepo.findAll()) {
                existingProductIds.add(product.getProductId());
            }

            for (Product product : products) {
                String description = product.getProductDescription();
                //checks for product with active description
                if ("Active".equals(description)) {
                    int productId = product.getProductId();

                    //checks unique product id
                    if (!existingProductIds.contains(productId)) {
                        // If product ID is not present, save the product
                        productRepo.save(product);
                        existingProductIds.add(productId);
                    } else {
                        throw new DuplicateProductIdException("Duplicate Product ID: " + productId);
                    }

                } else {
                    System.out.print("Inactive Product");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Custom exception class
    class DuplicateProductIdException extends RuntimeException {
        public DuplicateProductIdException(String message) {
            super(message);
        }
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}

