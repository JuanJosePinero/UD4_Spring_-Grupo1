package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ProFamily;
import com.example.demo.service.ProFamilyService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/proFamily")
public class ProFamilyController {


	@Autowired
	@Qualifier("proFamilyService")
	 private ProFamilyService proFamilyService;
	
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllProFam() {
        List<ProFamily> proFamilies = proFamilyService.getAll();
        if (proFamilies.isEmpty()) {
        	return new ResponseEntity<>(proFamilies, HttpStatus.NO_CONTENT);
        }
        System.out.println("Familias profesionales: "+proFamilies);
        return ResponseEntity.ok(proFamilies);
    }

}
