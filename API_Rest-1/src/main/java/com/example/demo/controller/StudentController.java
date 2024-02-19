package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Student;
import com.example.demo.model.ServicioModel;
import com.example.demo.service.ProFamilyService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	@Qualifier("servicioService")
	 private ServicioService servicioService;

	@Autowired
	@Qualifier("studentService")
	private StudentService studentService;
	
	@Autowired
	@Qualifier("proFamilyService")
	private ProFamilyService proFamilyService;
	
	private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";

	
	@GetMapping("/viewServices")
	public ResponseEntity<List<ServicioDTO>> viewServices(HttpServletRequest request) {
		
        int alumnoId = getUserIdFromToken(request);
        try {
	        List<ServicioDTO> serviceList = studentService.getServiceByStudentProfesionalFamily(alumnoId);
	      
	        return new ResponseEntity<>(serviceList, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@GetMapping("/viewServicesAssigned")
    public ResponseEntity<List<ServicioDTO>> viewAssignedServices(HttpServletRequest request) {
		int alumnoId = getUserIdFromToken(request);
        List<ServicioDTO> serviceList = studentService.getAssignedServiceByStudentProfesionalFamily(alumnoId);
        if(serviceList.isEmpty())
        	return null;
        return new ResponseEntity<>(serviceList, HttpStatus.OK);
    }
    
    @GetMapping("/viewServicesUnassigned")
    public ResponseEntity<List<ServicioDTO>> viewUnassignedServices(HttpServletRequest request) {
    	int alumnoId = getUserIdFromToken(request);
        List<ServicioDTO> serviceList = studentService.getUnassignedServiceByStudentProfesionalFamily(alumnoId);
        if(serviceList.isEmpty())
        	return null;
        return new ResponseEntity<>(serviceList, HttpStatus.OK);
    }
    
    private int getUserIdFromToken(HttpServletRequest request) {
        Claims claims = getToken(request);
        return (Integer) claims.get("userId");
    }
    
    
    private Claims getToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }
	

}