package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ProFamilyDTO;
import com.example.demo.entity.ProFamily;

@Component("proFamilyConverter")
public class ProFamilyConverter {

	public ProFamily transform(ProFamilyDTO proFamilyDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(proFamilyDTO, ProFamily.class);
	}
	
	public ProFamilyDTO transform(ProFamily proFamily) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(proFamily, ProFamilyDTO.class);
	}
	
}