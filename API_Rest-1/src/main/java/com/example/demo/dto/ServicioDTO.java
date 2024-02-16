package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTO {
	private int id;
	private String title;
	private String description;
	private int finished;
	private String comment;
	private int valoration;
}
