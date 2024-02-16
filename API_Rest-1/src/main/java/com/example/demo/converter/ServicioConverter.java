package com.example.demo.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.Servicio;

@Component("servicioConverter")
public class ServicioConverter {
	
	public Servicio transform(ServicioDTO servicioDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(servicioDTO, Servicio.class);
	}
	
	public ServicioDTO transform(Servicio servicio) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(servicio, ServicioDTO.class);
	}
	
	public List<ServicioDTO> transform2DTO(List<Servicio> servicios) {
	    List<ServicioDTO> serviciosDTO = new ArrayList<>();
	    for (Servicio s : servicios) {
	    	serviciosDTO.add(transform(s));
	    }
	    return serviciosDTO;
	}
	
	public List<Servicio> transform(List<ServicioDTO> serviciosDTO) {
	    List<Servicio> servicios = new ArrayList<>();
	    for (ServicioDTO s : serviciosDTO) {
	    	servicios.add(transform(s));
	    }
	    return servicios;
	}

}
