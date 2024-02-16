package com.example.demo.converter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;

@Component("studentConverter")
public class StudentConverter {

	public Student transform(StudentDTO studentDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(studentDTO, Student.class);
	}
	
	public StudentDTO transform(Student student) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(student, StudentDTO.class);
	}
	public List<StudentDTO> transform(List<Student> students) {
	    List<StudentDTO> studentDTOs = new ArrayList<>();
	    for (Student student : students) {
	    	studentDTOs.add(transform(student));
	    }
	    return studentDTOs;
	}

}