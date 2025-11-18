package com.salesSavvy.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.salesSavvy.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    

    Optional<Product> findByName(String name);
    
    Optional<Product> findByNameIgnoreCase(String name);
    

    List<Product> findByCategoryIgnoreCase(String category);
    
 
    List<Product> findByNameContainingIgnoreCase(String name);
    
    
//    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    boolean existsByNameIgnoreCase(String name);
    
    
    
    @Query("SELECT p FROM Product p " +
    	       "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
    	       "AND (:category IS NULL OR LOWER(p.category) = LOWER(:category))")
    	List<Product> findProductsByCriteria(@Param("name") String name,
    	                                     @Param("category") String category);

   
    // Find all active products (assuming you have an 'active' field)
    // List<Product> findByActiveTrue();
}
