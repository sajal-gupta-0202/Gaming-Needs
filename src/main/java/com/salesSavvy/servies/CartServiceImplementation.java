package com.salesSavvy.servies;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesSavvy.DTO.CartDTO;
import com.salesSavvy.DTO.CartItemDTO;
import com.salesSavvy.DTO.ProductDTO;
import com.salesSavvy.entities.Cart;
import com.salesSavvy.entities.CartItem;
import com.salesSavvy.entities.Product;
import com.salesSavvy.entities.Users;
import com.salesSavvy.repositories.CartRepository;
import com.salesSavvy.repositories.ProductRepository;
import com.salesSavvy.repositories.UsersRepository;

@Service
public class CartServiceImplementation implements CartService {
	
	    @Autowired private CartRepository cartRepo;
	    @Autowired private UsersRepository userRepo;
	    @Autowired private ProductRepository productRepo;

	    public Cart getCart(String username) {
	        Users user = userRepo.findByUsername(username);
	        return cartRepo.findByUser(user);
	    }

	    public Cart addToCart(String username, Long productId, int quantity) {
	        Users user = userRepo.findByUsername(username);
	        Cart cart = user.getCart();
	        if (cart == null) {
	            cart = new Cart();
	            cart.setUser(user);
	            cartRepo.save(cart);
	            user.setCart(cart);
	        }

	        Product product = productRepo.findById(productId).orElseThrow();

	        Optional<CartItem> existing = cart.getCartItems().stream()
	            .filter(item -> item.getProduct().getId() == (productId))
	            .findFirst();

	        if (existing.isPresent()) {
	            existing.get().setQuantity(existing.get().getQuantity() + quantity);
	        } else {
	            CartItem item = new CartItem();
	            item.setCart(cart);
	            item.setProduct(product);
	            item.setQuantity(quantity);
	            cart.getCartItems().add(item);
	        }

	        return cartRepo.save(cart);
	    }

	    public Cart removeFromCart(String username, Long productId) {
	        Users user = userRepo.findByUsername(username);
	        Cart cart = cartRepo.findByUser(user);

	        cart.getCartItems().removeIf(item -> 
	            item.getProduct().getId() == (productId));

	        return cartRepo.save(cart);
	    }
	    
	    public CartDTO convertToDTO(Cart cart) {
	        CartDTO dto = new CartDTO();
	        dto.setId(cart.getId());
	        dto.setUsername(cart.getUser().getUsername());

	        List<CartItemDTO> itemDTOs = cart.getCartItems().stream()
	            .map(this::convertToItemDTO)
	            .collect(Collectors.toList());

	        dto.setItems(itemDTOs);
	        return dto;
	    }

	    public CartItemDTO convertToItemDTO(CartItem item) {
	        CartItemDTO dto = new CartItemDTO();
	        dto.setId(item.getId());
	        dto.setQuantity(item.getQuantity());

	        Product p = item.getProduct();
	        ProductDTO productDTO = new ProductDTO();
	        productDTO.setId(p.getId());
	        productDTO.setName(p.getName());
	        productDTO.setPrice(p.getPrice());

	        dto.setProduct(productDTO);
	        return dto;
	    }

	    @Override
	    public List<CartItem> getItems(String username) {
	        Users u = userRepo.findByUsername(username);
	        if (u == null || u.getCart() == null) {
	            return Collections.emptyList();
	        }
	        return u.getCart().getCartItems();
	    }
	    
	    @Override
	    public List<CartItem> cloneItems(String username) {
	        return getItems(username).stream().map(src -> {
	            CartItem copy = new CartItem();
	            copy.setProduct(src.getProduct());   
	            copy.setQuantity(src.getQuantity());
	            copy.setCart(null);                  
	            return copy;
	        }).toList();
	    }

	    @Override
	    public void clearCart(String username) {
	        Users u = userRepo.findByUsername(username);
	        if (u != null && u.getCart() != null) {
	            u.getCart().getCartItems().clear();
	            cartRepo.save(u.getCart());
	        }
	    }

		
	}
