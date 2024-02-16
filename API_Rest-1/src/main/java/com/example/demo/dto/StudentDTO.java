package com.example.demo.dto;

import com.example.demo.entity.ProFamily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
	private int id;
	private String email;
	private String name, surname, role;
	private ProFamily profesionalFamily;
}
