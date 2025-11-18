package com.salesSavvy.servies;

import java.util.List;
import com.salesSavvy.entities.Product;

public interface ProductService {
    

    String addProduct(Product product);
    String updateProduct(Product product);
    String deleteProduct(Long id);
    
    Product getProductById(Long id);
    Product searchProductByName(String name);
    List<Product> searchProductByCategory(String category);
    List<Product> searchProductsByName(String name); // For partial matches
    List<Product> getAllProducts();

//    boolean existsByName(String name);
//    List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);
    List<Product> searchProducts(String name, String category);
    
    // Stock management
//    boolean updateStock(Long productId, Integer newStock);
//    boolean isProductAvailable(Long productId, Integer requiredQuantity);
}