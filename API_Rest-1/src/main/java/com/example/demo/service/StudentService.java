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
	
	Student model2entity(StudentModel studentModel);
	
	public StudentModel entity2model(Student student);
	
	public List<ServicioDTO> getServiceByStudentProfesionalFamily(int id);
	public List<ServicioModel> getUnassignedServiceByStudentProfesionalFamily(int id);
	public List<ServicioModel> getAssignedServiceByStudentProfesionalFamily(int id);
	
	public StudentModel getStudentByName(String name);

//	Student getStudentByEmail(String email);
	
//	public Student getStudentByUsername(String username);
	
	
 

}
