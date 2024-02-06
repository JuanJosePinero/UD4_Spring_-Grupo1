package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.UserDTO;

public interface UserService {
	
	public User findUserByUsername(String username);
	
	public User register(UserDTO user);

}
