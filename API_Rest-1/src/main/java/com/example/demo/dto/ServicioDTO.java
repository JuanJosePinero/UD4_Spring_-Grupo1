package com.example.demo.dto;

import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
    private ProFamily profesionalFamilyId;
	@JsonIgnore
    private Student studentId;
	private int valoration;
}
