package com.salesSavvy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salesSavvy.DTO.CartRequestDTO;
import com.salesSavvy.entities.Cart;
import com.salesSavvy.servies.CartService;
@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired private CartService cartService;

    @GetMapping("/{username}")
    public Cart getCart(@PathVariable String username) {
        return cartService.getCart(username);
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody CartRequestDTO dto) {
        return cartService.addToCart(dto.getUsername(), dto.getProductId(), dto.getQuantity());
    }

    @DeleteMapping("/remove")
    public Cart removeFromCart(@RequestParam String username,
                               @RequestParam Long productId) {
        return cartService.removeFromCart(username, productId);
    }
    
    @DeleteMapping("/clear")
    public void clearFromCart(@RequestParam String username) {
         cartService.clearCart(username);
    }
}

