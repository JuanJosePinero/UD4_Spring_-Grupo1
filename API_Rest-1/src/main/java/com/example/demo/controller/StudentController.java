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
import com.example.demo.model.ServicioModel;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	@Qualifier("servicioService")
	 private ServicioService servicioService;

	@Autowired
	@Qualifier("studentService")
	private StudentService studentService;
	
//	@GetMapping("/viewServices")
//	public ResponseEntity<List<ServicioModel>> viewServices(@RequestParam("studentId") int studentId) {
//
//		List<ServicioModel> serviceList = studentService.getServiceByStudentProfesionalFamily(studentId);
//		return new ResponseEntity<>(serviceList, HttpStatus.OK);
//	}
	
	@GetMapping("/viewServices")
	public ResponseEntity<List<ServicioDTO>> viewServices() {
	   /* if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }*/

	    try {
//	        Integer studentId = Integer.parseInt(authentication.getName());

	        List<ServicioDTO> serviceList = studentService.getServiceByStudentProfesionalFamily(1);
	        
	        return new ResponseEntity<>(serviceList, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }
	}

	
	@GetMapping("/viewServicesAssigned")
    public ResponseEntity<List<ServicioModel>> viewAssignedServices(@RequestParam("studentId") int studentId) {

        List<ServicioModel> serviceList = studentService.getAssignedServiceByStudentProfesionalFamily(studentId);
        return new ResponseEntity<>(serviceList, HttpStatus.OK);
    }
    
    @GetMapping("/viewServicesUnassigned")
    public ResponseEntity<List<ServicioModel>> viewUnassignedServices(@RequestParam("studentId") int studentId) {

        List<ServicioModel> serviceList = studentService.getUnassignedServiceByStudentProfesionalFamily(studentId);
        return new ResponseEntity<>(serviceList, HttpStatus.OK);
    }
	

}