package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Student;
import com.example.demo.model.ServicioModel;
import com.example.demo.model.StudentModel;

public interface StudentService {

	List<Student> listAllStudents();

	Student getStudentById(int id);

	Student getStudentByName(String name);

	public List<ServicioDTO> getServiceByStudentProfesionalFamily(int id);

	public List<ServicioDTO> getUnassignedServiceByStudentProfesionalFamily(int id);

	public List<ServicioDTO> getAssignedServiceByStudentProfesionalFamily(int id);

}
