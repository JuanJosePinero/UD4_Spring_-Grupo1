package com.example.demo.model;

import lombok.Data;

//Model
@Data
public class UserDTO {

	private long id;
	private String username;
	private String password;
	private boolean enabled;
	private String role;
	private String token;
	
	public UserDTO() {
		super();
	}

	public UserDTO(long id, String username, String password, boolean enabled, String role, String token) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.token = token;
	}

	public UserDTO(long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
}
