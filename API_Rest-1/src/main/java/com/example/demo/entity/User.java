package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	@OneToOne(mappedBy="id")
	private Student studentID;
	@OneToOne(mappedBy="id")
	private Business businessID;
}
