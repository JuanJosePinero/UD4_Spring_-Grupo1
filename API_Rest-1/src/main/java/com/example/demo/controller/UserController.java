package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("studentService")
	private StudentService studentService;

	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String pwd) {
	    Student student = userService.login(email, pwd);
	    if (student != null) {
	        String token = getJWTToken(student);
	        AuthenticationResponseDTO response = new AuthenticationResponseDTO(student.getId(),
	                student.getName(),
	                student.getEmail(),
	                student.getRole(),
	                token);

	        return ResponseEntity.ok(response);
	        
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    
	    
	}

	
	
	@PostMapping("/register")
	public ResponseEntity<?> saveStudent(@RequestBody com.example.demo.model.StudentModel studentModel){
		List<StudentDTO> existingStudents = studentService.getAllStudentByEmail(studentModel.getEmail());
        if (existingStudents == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This email is already registered");
        }
        
        Student newStudent = userService.register(studentModel);
        return ResponseEntity.ok(newStudent);
    }
	
	
	private String getJWTToken(Student usuario) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(usuario.getRole());

		String token = Jwts.builder()
				.setId("softtekJWT")
				.setSubject(usuario.getEmail())
				.claim("userId", usuario.getId())
				.claim("authorities", grantedAuthorities.stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 6000000))// Tiempo de caducidad del token aumentada
																				// durante desarrollo. Disminuir si
																				// fuese neceario.
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
				.compact();

		return "Bearer " + token;
	}
	

}
