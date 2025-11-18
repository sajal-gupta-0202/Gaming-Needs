package com.salesSavvy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salesSavvy.entities.Product;
import com.salesSavvy.servies.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    // Create a new product (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> addProduct(@RequestBody Product product) {
        log.info("Request to add product: {}", Objects.toString(product.getName(), "n/a"));
        String result = service.addProduct(product);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);

        return ResponseEntity.ok(response);
    }

    // Update a product (ADMIN only)
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateProduct(@RequestBody Product product) {
        log.info("Request to update product id: {}", product.getId());
        String result = service.updateProduct(product);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);

        return ResponseEntity.ok(response);
    }

    // Search products by name and/or category (USER or PUBLIC depending on your security)
    // Example: GET /products/search?name=ps5&category=consoles
    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> searchByNameOrCategory(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        log.info("Searching products. name='{}', category='{}'", name, category);
        List<Product> results = service.searchProducts(name, category);
        return ResponseEntity.ok(results);
    }

    // Delete a product by id (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        log.info("Request to delete product id: {}", id);
        String result = service.deleteProduct(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    // Get all products (USER)
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Fetching all products");
        List<Product> list = service.getAllProducts();
        return ResponseEntity.ok(list);
    }

    // Get single product by id (USER)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Fetching product by id: {}", id);
        Product product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }
}

