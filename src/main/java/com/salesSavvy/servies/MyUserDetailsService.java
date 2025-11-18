package com.salesSavvy.servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesSavvy.entities.UserPrincipal;
import com.salesSavvy.entities.Users;
import com.salesSavvy.repositories.UsersRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UsersRepository repo;

	public void addUser(Users user) {
		repo.save(user);
		
	}

	public Users getUser(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = repo.findByUsername(username);
		
		if(user == null) {
			System.out.println("User not found!");
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserPrincipal(user);
	}
}
