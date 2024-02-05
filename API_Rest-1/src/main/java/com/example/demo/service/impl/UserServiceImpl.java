package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
