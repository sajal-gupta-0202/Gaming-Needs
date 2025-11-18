package com.salesSavvy.servies;


import java.util.List;

import com.salesSavvy.entities.UserLoginData;
import com.salesSavvy.entities.Users;

public interface UsersService {

	Users getUser(String username);

	void signUp(Users user);


	boolean validate(String username, String password);

	List<Users> getAllUser();

	String verify(UserLoginData user);




}
