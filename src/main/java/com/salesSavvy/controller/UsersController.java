package com.salesSavvy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.salesSavvy.DTO.CartDTO;
import com.salesSavvy.entities.Cart;
import com.salesSavvy.entities.UserLoginData;
import com.salesSavvy.entities.Users;
import com.salesSavvy.servies.CartService;
import com.salesSavvy.servies.UsersService;

@CrossOrigin("*")
@RestController
public class UsersController {
	@Autowired
	UsersService service;
	
	@Autowired
	CartService cartService;
	
	@PostMapping("/signup")
	public String signUp(@RequestBody Users user) {
		System.out.println(user);
		String msg = "";
		String username = user.getUsername();
		Users u = service.getUser(username);
		if(u == null) {
			service.signUp(user);
			msg = "User created successfully!";
		}
		else
			msg = "Username already exists!";
		return msg;
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<String> signIn(@RequestBody UserLoginData userLoginData) {

        try {

            String token = service.verify(userLoginData);


            if (token != null && !token.equals("fail")) { 
                return ResponseEntity.ok(token); 
            } else {

                return new ResponseEntity<>("Authentication failed: Invalid credentials or token generation issue", HttpStatus.UNAUTHORIZED);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during login: " + e.getMessage()); 
            return new ResponseEntity<>("An unexpected error occurred during login.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
	@PostMapping("/updateUsers")
	public Users updateUsers() {
		return service.getUser("Rohit");
		}
	
	@GetMapping("/{username}")
	public CartDTO getCart(@PathVariable String username) {
	    Cart cart = cartService.getCart(username);
	    return cartService.convertToDTO(cart);
	}
	
	@GetMapping("/admin/getUsers")
	public List<Users> getCustomers() {
		return service.getAllUser();
	}
	
	
	
}
	