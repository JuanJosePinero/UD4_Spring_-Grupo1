package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Business;
import com.example.demo.model.ServicioModel;
import com.example.demo.service.BusinessService;
import com.example.demo.service.ProFamilyService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api")
public class BusinessController {

	@Autowired
	@Qualifier("servicioService")
	 private ServicioService servicioService;

	@Autowired
	@Qualifier("studentService")
    private StudentService studentService;
	
	@Autowired
	@Qualifier("businessService")
    private BusinessService businessService;
	
	@Autowired
	@Qualifier("proFamilyService")
	 private ProFamilyService proFamilyService;
	
	// Crear un nuevo servicio por parte de la empresa logueada
    @PostMapping("/servicios")
    public ResponseEntity<ServicioModel> createServicio(@RequestBody ServicioModel servicioModel) {
    	Business business = getCurrentBusiness();

        servicioModel.setBusinessId(business);
        ServicioModel createdServicio = servicioService.addServicio(servicioModel);

        return new ResponseEntity<>(createdServicio, HttpStatus.CREATED);
    }

    // Recuperar un determinado servicio de la empresa logueada
    @GetMapping("/servicios/{servicioId}")
    public ResponseEntity<ServicioModel> getServicioById(@PathVariable("servicioId") int servicioId) {
    	Business business = getCurrentBusiness();

        ServicioModel servicio = servicioService.getServicioById(servicioId);
        if (servicio != null && servicio.getBusinessId().getId() == business.getId()) {
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Recuperar todos los servicios de la empresa logueada
    @GetMapping("/servicios")
    public ResponseEntity<List<ServicioModel>> getAllServicios() {
    	Business business = getCurrentBusiness();

        List<ServicioModel> servicios = servicioService.getServicesByBusinessId(business);
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    // Actualizar un servicio de la empresa logueada
    @PutMapping("/servicios/{servicioId}")
    public ResponseEntity<ServicioModel> updateServicio(@PathVariable("servicioId") int servicioId,
                                                         @RequestBody ServicioModel servicioModel) {
    	Business business = getCurrentBusiness();

        servicioModel.setId(servicioId);
        ServicioModel updatedServicio = servicioService.updateServicio(servicioModel);
        if (updatedServicio != null && updatedServicio.getBusinessId().getId() == business.getId()) {
            return new ResponseEntity<>(updatedServicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un servicio de la empresa logueada
    @DeleteMapping("/servicios/{servicioId}")
    public ResponseEntity<Void> deleteServicio(@PathVariable("servicioId") int servicioId) {
    	Business business = getCurrentBusiness();

        int deleteStatus = servicioService.deleteServicio(servicioId);
        if (deleteStatus == 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Recuperar los servicios de una empresa filtrando por familia profesional
    @GetMapping("/servicios/filtrar")
    public ResponseEntity<List<ServicioModel>> filterServiciosByProFamily(@RequestParam("familiaProfesional") String familiaProfesional) {
    	Business business = getCurrentBusiness();

        List<ServicioModel> servicios = servicioService.findServiciosByProFamily(familiaProfesional);
        // Filtrar solo los servicios de la empresa logueada
        servicios.removeIf(servicio -> servicio.getBusinessId().getId() != business.getId());

        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }
    
    private Business getCurrentBusiness() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return businessService.getIdByUsername(username);
    }

}
