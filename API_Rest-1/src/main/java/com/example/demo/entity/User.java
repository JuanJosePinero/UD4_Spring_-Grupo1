package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class User {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="username", unique=true, nullable=false)
	private String username;
	@Column(name="password", nullable=false)
	private String password;
	private int enabled;
	private String role;
	private String token;

	public User() {
		super();
	}
	
	public User(int id, String username, String password, int enabled, String role, String token) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.token = token;
	}
	
	
}
