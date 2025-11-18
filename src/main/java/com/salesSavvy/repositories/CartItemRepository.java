package com.salesSavvy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesSavvy.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
