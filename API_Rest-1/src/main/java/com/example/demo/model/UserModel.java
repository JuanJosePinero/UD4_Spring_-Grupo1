package com.example.demo.model;

import com.example.demo.entity.User;

import lombok.Data;

//Model

public class UserModel extends User{

	private int id;
	private String username;
	private String password;
	private int enabled;
	private String role;
	private String token;
	
	
}
