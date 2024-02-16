package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/viewServices/{profesionalFamilyId}")
	public ResponseEntity<List<ServicioDTO>> viewServices(@PathVariable(name = "profesionalFamilyId", required = false) Integer profesionalFamilyId, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
	    if (profesionalFamilyId == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	    try {
	        List<ServicioDTO> serviceList = studentService.getServiceByStudentProfesionalFamily(profesionalFamilyId);
	        
	        // Aplicar paginación
	        int startIndex = page * size;
	        int endIndex = Math.min(startIndex + size, serviceList.size());
	        List<ServicioDTO> paginatedServiceList = serviceList.subList(startIndex, endIndex);
	        
	        return new ResponseEntity<>(paginatedServiceList, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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