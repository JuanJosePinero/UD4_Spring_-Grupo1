package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Configuration
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("studentRepository")
	private StudentRepository studentRepository;

	

	@Override
	public User findUsuario(String username, String pwd) {
		
		return null;
	}

	@Override
	public Student login(String email, String pwd) {
	    Student student = studentRepository.findByEmail(email);
	    if (student != null && student instanceof Student && passwordEncoder().matches(pwd, student.getPassword())) {
	        return student;
	    }
	    return null;
	}

	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 @Override
	    public Student register(StudentModel studentModel) {
	        Student student = new Student();
	        student.setName(studentModel.getName());
	        student.setSurname(studentModel.getSurname());
	        student.setEmail(studentModel.getEmail());
	        student.setPassword(passwordEncoder().encode(studentModel.getPassword()));
	        student.setProfesionalFamily(studentModel.getProfesionalFamily());
	        student.setEnabled(1);
	        student.setDeleted(0);
	        student.setRole("ROLE_STUDENT");

	        return userRepository.save(student);
	    }

}
