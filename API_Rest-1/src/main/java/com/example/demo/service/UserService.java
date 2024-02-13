package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.UserModel;

public interface UserService {

	User getUserByUsername(String username);

	User findUsuario(String username, String pwd);
	
	User login(String username, String pwd);

	User register(UserModel userModel);
	
}
