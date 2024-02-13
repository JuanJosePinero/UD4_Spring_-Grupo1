package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProFamily;
import com.example.demo.entity.User;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Configuration
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Override
	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user != null) {
			return user;
		}
		return null;
	}

	@Override
	public User findUsuario(String username, String pwd) {
		
		return null;
	}

	@Override
	public User login(String username, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public User register(UserModel userModel) {
		ProFamily proFam = null;
		userModel.setPassword(passwordEncoder().encode(userModel.getPassword()));
		userModel.setEnabled(1);
		userModel.setRole("ROLE_STUDENT");
	
		return null;
	}

}
