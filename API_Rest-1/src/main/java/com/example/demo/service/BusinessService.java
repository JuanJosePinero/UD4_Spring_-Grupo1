package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BusinessDTO;
import com.example.demo.entity.Business;
import com.example.demo.model.BusinessModel;


public interface BusinessService {
	
	List<Business> getAllBusiness();

	Business getBusinessById(int id);

	public BusinessModel entity2model(Business	business);
	
	public Business model2entity(BusinessModel	businessModel);
	
	public Business getIdByUsername(String username);
	
	public BusinessDTO getBusinessDTOByStudentId(int id);

	public Business getBusinessByStudentId(int id);

	public Business getBusinessByEmail(String email);
	
}
