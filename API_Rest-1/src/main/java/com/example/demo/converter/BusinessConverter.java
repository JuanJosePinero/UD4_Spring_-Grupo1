package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.BusinessDTO;
import com.example.demo.entity.Business;

@Component("businessConverter")
public class BusinessConverter {

	public Business transform(BusinessDTO businessDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(businessDTO, Business.class);
	}
	
	public BusinessDTO transform(Business business) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(business	, BusinessDTO.class);
	}
	
}