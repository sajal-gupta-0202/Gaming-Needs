package com.salesSavvy.servies;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.salesSavvy.entities.UserLoginData;
import com.salesSavvy.entities.Users;
import com.salesSavvy.repositories.UsersRepository;

@Service
public class UsersServiceImplementation implements UsersService {
    
    @Autowired
    UsersRepository repo;
    
    @Autowired
    JWTService jwtService;
    
    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void signUp(Users user) {
    	
    	if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    	
        // Check if user already exists
        if (repo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    @Override
    public Users getUser(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public boolean validate(String username, String password) {
        Users u = repo.findByUsername(username);
        return u != null && passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public List<Users> getAllUser() {
        return repo.findAll();
    }

    @Override
    public String verify(UserLoginData user) {
    	System.out.println(user);
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
        return "fail";
    }
}