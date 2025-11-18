package com.salesSavvy.DTO;



public class ProductDTO {
    
    private Long id;
    
    private String name;
    
    private Double price;
    
    private String category;
    
    private String description;
    
    // Constructors
    public ProductDTO() {}
    
    public ProductDTO(String name, Double price, String category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}