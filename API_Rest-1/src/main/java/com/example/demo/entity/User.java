package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private long id;
	@Column(name="username", unique=true, nullable=false)
	private String username;
	@Column(name="password", nullable=false)
	private String password;
	private boolean enabled;
	private String role;
	private String token;
	
}
