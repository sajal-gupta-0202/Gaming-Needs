package com.salesSavvy.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private long userId;

    private String name;
    private String description;
    private double price;
    private String photo;
    private String category;

    @ElementCollection
    @CollectionTable(
        name = "product_reviews")
    @Column(name = "review")
    private List<String> reviews = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonBackReference(value = "product-cartitem")
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();


    public Product() {
        super();
    }


	public Product(long id, long userId, String name, String description, double price, String photo, String category,
			List<String> reviews, List<CartItem> cartItems) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.photo = photo;
		this.category = category;
		this.reviews = reviews;
		this.cartItems = cartItems;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public List<String> getReviews() {
		return reviews;
	}


	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", userId=" + userId + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", photo=" + photo + ", category=" + category + ", reviews=" + reviews
				+ ", cartItems=" + cartItems + "]";
	}
    
    
    
}