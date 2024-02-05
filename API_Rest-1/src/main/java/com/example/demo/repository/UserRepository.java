package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository("userRepository")
public interface UserRepository {
	public abstract User findByUsername(int username);

}
