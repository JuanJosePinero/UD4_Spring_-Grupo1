package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.model.StudentModel;
import com.example.demo.model.UserModel;

public interface UserService {


	User findUsuario(String username, String pwd);
	
	Student login(String username, String pwd);

	Student register(StudentModel studentModel);
	
}
