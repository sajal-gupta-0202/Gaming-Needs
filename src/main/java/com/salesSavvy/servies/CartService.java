package com.salesSavvy.servies;

import java.util.List;

import com.salesSavvy.DTO.CartDTO;
import com.salesSavvy.entities.Cart;
import com.salesSavvy.entities.CartItem;


public interface CartService {

	Cart getCart(String username);

	Cart addToCart(String username, Long productId, int quantity);

	Cart removeFromCart(String username, Long productId);

	CartDTO convertToDTO(Cart cart);

	List<CartItem> cloneItems(String username);


	List<CartItem> getItems(String username);

	void clearCart(String username);

	
	

}
