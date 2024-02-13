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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@Autowired
	@Qualifier("studentService")
	private StudentService studentService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestParam("user") String username,
			@RequestParam("password") String pwd) {
		User usuario = userService.findUsuario(username, pwd);
		if (usuario != null) {
			String token = getJWTToken(usuario);
			String nombreEmpresa = usuario.getEmpresa() != null ? usuario.getEmpresa().getNombre() : "";
			String nombreAlumno = usuario.getAlumno() != null ? usuario.getAlumno().getNombre() : "";
			LoginResponseDTO response = new LoginResponseDTO(usuario.getId(),
					nombreEmpresa.equalsIgnoreCase("") ? nombreEmpresa : nombreAlumno,
					usuario.getEmail(),
					usuario.getRole(),
					token);
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/register")
	public com.example.demo.entity.Student saveUser(@RequestBody com.example.demo.model.StudentModel studentModel){
		return studentService.register(studentModel);
		
	}
	
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	

}
