package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Servicio;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StudentModel extends UserDTO {

	private String name, surname, password, role;
	private int enabled;
	private int deleted;
	private ProFamily profesionalFamily;
	private List<Servicio> servicios;
	private int idStudent;
	
	public StudentModel(String name, String surname, String password, String role, int enabled, int deleted,
			ProFamily profesionalFamily, List<Servicio> servicios, int idStudent) {
		super();
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.deleted = deleted;
		this.profesionalFamily = profesionalFamily;
		this.servicios = servicios;
		this.idStudent = idStudent;
	}

	public StudentModel() {
		super();
	}
	
	
	
	
}

