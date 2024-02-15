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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.entity.Student;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

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
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
	    
	    
	}

	
	
	@PostMapping("/register")
	public com.example.demo.entity.Student saveStudent(@RequestBody com.example.demo.model.StudentModel studentModel){
		return userService.register(studentModel);
		
	}
	
	private String getJWTToken(Student student) {
	    String secretKey = "mySecretKey";
	    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
	            .commaSeparatedStringToAuthorityList(student.getRole());

	    String token = Jwts
	            .builder()
	            .setId("softtekJWT")
	            .setSubject(String.valueOf(student.getId()))
	            .claim("authorities",
	                    grantedAuthorities.stream()
	                            .map(GrantedAuthority::getAuthority)
	                            .collect(Collectors.toList()))
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 600000))
	            .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
	    return "Bearer " + token;
	}
	

}
