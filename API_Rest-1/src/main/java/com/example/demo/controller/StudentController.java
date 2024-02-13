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

import com.example.demo.model.ServicioModel;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	@Qualifier("servicioService")
	 private ServicioService servicioService;

	@Autowired
	@Qualifier("studentService")
	private StudentService studentService;
	
	@GetMapping("/viewServices")
	public ResponseEntity<List<ServicioModel>> viewServices(@RequestParam("studentId") int studentId) {

		List<ServicioModel> serviceList = studentService.getServiceByStudentProfesionalFamily(studentId);
		return new ResponseEntity<>(serviceList, HttpStatus.OK);
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