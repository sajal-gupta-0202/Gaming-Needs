package com.salesSavvy.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Users {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false, unique = true)
	String username;
	
	@Column(nullable = false, unique = true)
	String email;
	
	@Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
	
    @Column(nullable = false)
	String password;
	
	String gender;
	
	LocalDate dateOfBirth;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;
	
	@Column(name = "phone_number")
    private String phoneNumber;
	
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
	
	// Enums
    public enum Gender {
        MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
    }
    
    public enum Role {
        USER, ADMIN
    }

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Long id, String username, String email, String firstName, String lastName, String password,
			String gender, LocalDate dateOfBirth, Role role, String phoneNumber, Cart cart) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.cart = cart;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password + ", gender=" + gender + ", dateOfBirth="
				+ dateOfBirth + ", role=" + role + ", phoneNumber=" + phoneNumber +"]";
	}
	
    
	
}