package com.salesSavvy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesSavvy.entities.Cart;
import com.salesSavvy.entities.Users;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUser(Users user);

}
