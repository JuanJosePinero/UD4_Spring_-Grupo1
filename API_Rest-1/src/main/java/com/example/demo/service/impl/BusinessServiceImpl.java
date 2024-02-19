package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.example.demo.converter.BusinessConverter;
import com.example.demo.dto.BusinessDTO;
import com.example.demo.entity.Business;
import com.example.demo.entity.Student;
import com.example.demo.model.BusinessModel;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ServicioRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.BusinessService;

@Configuration
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	@Qualifier("businessRepository")
	private BusinessRepository businessRepository;
	
	@Autowired
	@Qualifier("studentRepository")
	private StudentRepository studentRepository;
	
	@Autowired
	@Qualifier("servicioRepository")
	private ServicioRepository servicioRepository;

    
    @Override
    public Business model2entity(BusinessModel businessModel) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(businessModel, Business.class);
	}
    
    @Override
	public BusinessModel entity2model(Business business) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(business, BusinessModel.class);
	}
    
	@Override
	public List<Business> getAllBusiness() {
		return businessRepository.findAll();
	}

	@Override
	public Business getBusinessById(int id) {
		Optional<Business> optionalBusiness = businessRepository.findById(id);
        return optionalBusiness.orElse(null);
	}


	@Override
	public Business getIdByUsername(String username) {
		Business business=businessRepository.findByName(username);
		return business;
	}

	@Override
	public Business getBusinessByStudentId(int id) {
		Student student = studentRepository.findById(id);
		String email = student.getEmail();
		List<Business> business = getAllBusiness();
		for (Business b : business) {
			if(b.getEmail().equalsIgnoreCase(email))
				return b;	
		}
		return null;
	}
	
	@Override
	public BusinessDTO getBusinessDTOByStudentId(int id) {
		Student student = studentRepository.findById(id);
		String email = student.getEmail();
		Business business = getBusinessByEmail(email);
		BusinessConverter bc = new BusinessConverter();
		return bc.transform(business);
	}

	@Override
	public Business getBusinessByEmail(String email) {	
		return businessRepository.findByEmail(email);
	}	

}
