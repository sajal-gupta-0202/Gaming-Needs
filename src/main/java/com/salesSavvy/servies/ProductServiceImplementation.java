package com.salesSavvy.servies;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salesSavvy.entities.Product;
import com.salesSavvy.repositories.ProductRepository;

@Service
@Transactional
public class ProductServiceImplementation implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImplementation.class);

    @Autowired
    private ProductRepository repo;

    @Override
    public String addProduct(Product product) {
        try {
            // Validation
            if (product == null) {
                return "Product cannot be null";
            }

            if (product.getName() == null || product.getName().trim().isEmpty()) {
                return "Product name is required";
            }

            if (product.getPrice() <= 0) {
                return "Product price must be greater than 0";
            }

            if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
                return "Product category is required";
            }

            // Check if product with same name already exists
            if (repo.existsByNameIgnoreCase(product.getName().trim())) {
                return "Product with this name already exists";
            }

            // Clean and set data
            product.setName(product.getName().trim());
            product.setCategory(product.getCategory().trim());
            if (product.getDescription() != null) {
                product.setDescription(product.getDescription().trim());
            }

            repo.save(product);
            log.info("Product saved: {}", product.getName());
            return "Product added successfully!";

        } catch (Exception e) {
            log.error("Error adding product", e);
            return "Error adding product: " + e.getMessage();
        }
    }

    @Override
    public String updateProduct(Product product) {
        try {
            if (product == null || product.getId() == null || product.getId() <= 0) {
                return "Product ID is required for update";
            }

            // Check if product exists
            Optional<Product> existingProductOpt = repo.findById(product.getId());
            if (!existingProductOpt.isPresent()) {
                return "Product not found";
            }

            Product existingProduct = existingProductOpt.get();

            // Validation & updates
            if (product.getName() != null && !product.getName().trim().isEmpty()) {
                // Check if another product has the same name
                Optional<Product> duplicateProduct = repo.findByNameIgnoreCase(product.getName().trim());
                if (duplicateProduct.isPresent() &&
                        !Objects.equals(duplicateProduct.get().getId(), product.getId())) {
                    return "Another product with this name already exists";
                }
                existingProduct.setName(product.getName().trim());
            }

            if (product.getPrice() != null && product.getPrice() > 0) {
                existingProduct.setPrice(product.getPrice());
            }

            if (product.getCategory() != null && !product.getCategory().trim().isEmpty()) {
                existingProduct.setCategory(product.getCategory().trim());
            }

            if (product.getDescription() != null) {
                existingProduct.setDescription(product.getDescription().trim());
            }

            repo.save(existingProduct);
            log.info("Product updated: id={}, name={}", existingProduct.getId(), existingProduct.getName());
            return "Product updated successfully!";

        } catch (Exception e) {
            log.error("Error updating product", e);
            return "Error updating product: " + e.getMessage();
        }
    }

    @Override
    public String deleteProduct(Long id) {
        try {
            if (id == null || id <= 0) {
                return "Invalid product ID";
            }

            if (!repo.existsById(id)) {
                return "Product not found";
            }

            repo.deleteById(id);
            log.info("Product deleted: id={}", id);
            return "Product deleted successfully!";

        } catch (Exception e) {
            log.error("Error deleting product", e);
            return "Error deleting product: " + e.getMessage();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        try {
            if (id == null || id <= 0) {
                return null;
            }

            return repo.findById(id).orElse(null);

        } catch (Exception e) {
            log.error("Error finding product by ID: {}", id, e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Product searchProductByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }

            return repo.findByNameIgnoreCase(name.trim()).orElse(null);

        } catch (Exception e) {
            log.error("Error finding product by name: {}", name, e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductByCategory(String category) {
        try {
            if (category == null || category.trim().isEmpty()) {
                return Collections.emptyList();
            }

            return repo.findByCategoryIgnoreCase(category.trim());

        } catch (Exception e) {
            log.error("Error finding products by category: {}", category, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return Collections.emptyList();
            }

            return repo.findByNameContainingIgnoreCase(name.trim());

        } catch (Exception e) {
            log.error("Error searching products by name: {}", name, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            log.error("Error getting all products", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProducts(String name, String category) {
        try {
            // Delegate to repository method for combined criteria if available
            return repo.findProductsByCriteria(name, category);
        } catch (Exception e) {
            log.error("Error searching products with criteria name='{}' category='{}'", name, category, e);
            return Collections.emptyList();
        }
    }
}
