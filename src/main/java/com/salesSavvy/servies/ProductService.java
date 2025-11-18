package com.salesSavvy.servies;

import java.util.List;
import com.salesSavvy.entities.Product;

/**
 * Service layer interface for product-related business operations.
 * Implementations should handle validation, mapping and repository access.
 */
public interface ProductService {

    /**
     * Add a new product to the catalog.
     * @param product product entity to save
     * @return a user-friendly result message
     */
    String addProduct(Product product);

    String updateProduct(Product product);
    String deleteProduct(Long id);

    Product getProductById(Long id);

    /**
     * Exact-name lookup (case-insensitive).
     * @param name exact product name
     * @return matching product or null
     */
    Product searchProductByName(String name);

    List<Product> searchProductByCategory(String category);

    /**
     * Partial-name search (contains, case-insensitive).
     * Useful for implementing search-as-you-type.
     */
    List<Product> searchProductsByName(String name); // For partial matches

    List<Product> getAllProducts();

    /**
     * Search by combined criteria (name and/or category). Implementation may
     * delegate to a custom repository query that handles null/empty args.
     */
    List<Product> searchProducts(String name, String category);

    // Stock management (optional)
    // boolean updateStock(Long productId, Integer newStock);
    // boolean isProductAvailable(Long productId, Integer requiredQuantity);
}
