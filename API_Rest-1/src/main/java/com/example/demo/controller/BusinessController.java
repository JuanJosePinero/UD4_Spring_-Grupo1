package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.converter.ServicioConverter;
import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.Business;
import com.example.demo.entity.Servicio;
import com.example.demo.model.ServicioModel;
import com.example.demo.service.BusinessService;
import com.example.demo.service.ProFamilyService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/business")
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

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "mySecretKey";

	// Crear un nuevo servicio por parte de la empresa logueada
	@PostMapping("/newServicio")
	public ResponseEntity<?> createServicio(@RequestBody ServicioModel servicioModel, HttpServletRequest request) {
		Business loggedBusiness = getCurrentBusiness(request);

		servicioModel.setBusinessId(loggedBusiness);

		Servicio existingServicio = servicioService.getOneServiceByBusinessId(loggedBusiness, servicioModel.getTitle(),
				servicioModel.getDescription());
		if (existingServicio != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A service with the same title and description already exists for this company.");
		}
		ServicioDTO newServicio = servicioService.addServicio(servicioModel);

		return new ResponseEntity<>(newServicio, HttpStatus.CREATED);
	}

	// Recuperar un determinado servicio de la empresa logueada
	@GetMapping("/servicios/{servicioId}")
	public ResponseEntity<ServicioDTO> getServicioById(@PathVariable("servicioId") int servicioId,
			HttpServletRequest request) {
		Business loggedBusiness = getCurrentBusiness(request);
		try {
			List<ServicioDTO> servicios = servicioService.getServicesByBusinessId(loggedBusiness);
			if (servicios.isEmpty()) {
				return null;
			}
			for (ServicioDTO s : servicios) {
				if (s.getId() == servicioId) {
					return ResponseEntity.ok(s);
				}
			}

		} catch (EntityNotFoundException e) {
			return null;
		}
		return null;

	}

	// Recuperar todos los servicios de la empresa logueada
	@GetMapping("/servicios")
	public ResponseEntity<?> getAllServicios(HttpServletRequest request) {
		Business loggedBusiness = getCurrentBusiness(request);
		try {
			List<ServicioDTO> servicioDTO = servicioService.getServicesByBusinessId(loggedBusiness);
			if (servicioDTO.isEmpty()) {
				return null;
			}
			return ResponseEntity.ok(servicioDTO);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Business not found");
		}
	}

	// Recuperar todos los servicios de la empresa logueada filtrando por familia profesional
	@GetMapping("/servicios/proFam/{proFamilyId}")
	public ResponseEntity<?> getServiciosByProFamily(HttpServletRequest request,
			@PathVariable("proFamilyId") int proFamilyId) {
		Business loggedBusiness = getCurrentBusiness(request);
		try {
			List<ServicioDTO> serviciosDTO = servicioService.getServicesByBusinessId(loggedBusiness);
			if (serviciosDTO.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This professional family has no services.");
			}
			List<ServicioDTO> servicioDTO = new ArrayList<>();
			for (ServicioDTO s : serviciosDTO) {
				if (s.getProfesionalFamilyId().getId() == proFamilyId)
					servicioDTO.add(s);

			}
			return ResponseEntity.ok(servicioDTO);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Business not found");
		}
	}

	// Actualizar un servicio de la empresa logueada
	@PutMapping("/servicios/{servicioId}")
	public ResponseEntity<ServicioDTO> updateServicio(@PathVariable("servicioId") int servicioId,
			@RequestBody ServicioModel servicio, HttpServletRequest request) {
		Business loggedBusiness = getCurrentBusiness(request);

		if (loggedBusiness.getId() == servicioService.getServicioById(servicioId).getBusinessId().getId()) {
			// Aunque el usuario meta un id distinto en el modelo, no cambiara gracias a esto. Control de errores :)
			servicio.setId(servicioId);
			Servicio updatedServicio = servicioService.updateServicio(servicio);
			ServicioConverter serviceConverter = new ServicioConverter();
			ServicioDTO serviceDTO = serviceConverter.transform(updatedServicio);
			return new ResponseEntity<>(serviceDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Eliminar un servicio de la empresa logueada
	@DeleteMapping("/servicios/{servicioId}")
	public ResponseEntity<Void> deleteServicio(@PathVariable("servicioId") int servicioId, HttpServletRequest request) {
		Business loggedBusiness = getCurrentBusiness(request);

		if (loggedBusiness.getId() == servicioService.getServicioById(servicioId).getBusinessId().getId()) {
			int deleteStatus = servicioService.deleteServicio(servicioId);
			if (deleteStatus == 1) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	private Business getCurrentBusiness(HttpServletRequest request) {
		Claims claims = getToken(request);
		int alumnoId = (Integer) claims.get("userId");
		return businessService.getBusinessByStudentId(alumnoId);
	}
	
	@GetMapping("/getLoggedBusiness")
	public ResponseEntity<?> getLoggedBusiness(HttpServletRequest request) {
		Claims claims = getToken(request);
		int alumnoId = (Integer) claims.get("userId");
		try {
		    Business empresa = businessService.getBusinessByStudentId(alumnoId);
		    return ResponseEntity.ok(empresa);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Business not found");
		}
	}

	private Claims getToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
}
