package com.salesSavvy.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salesSavvy.entities.Product;
import com.salesSavvy.servies.ProductService;



@CrossOrigin("*")
@RestController
public class ProductController {

	
	@Autowired
	ProductService service;
		
	
	@PostMapping("/addProduct")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> addProduct(@RequestBody Product product) {
        String result = service.addProduct(product);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);

        return ResponseEntity.ok(response);  
    }
	
	@PostMapping("/updateProduct")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> updateProduct(@RequestBody Product product) {
		String result =  service.updateProduct(product);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", result);
		
		return ResponseEntity.ok(response);
	}
	

	@GetMapping("/seachProduct")
	@PreAuthorize("hasRole('USER')")
	public List<Product> seachByCategory(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
		return service.searchProducts(name, category);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deletePRoduct(@PathVariable Long id) {
		System.out.println(id);
		return service.deleteProduct(id);
	}
	
	@GetMapping("/getAllProduct")
	@PreAuthorize("hasRole('USER')")
	public List<Product> getAllProduct(){
		return service.getAllProducts();
	}
	
	@GetMapping("/getProductById/{id}")
	@PreAuthorize("hasRole('USER')")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }
	
}
