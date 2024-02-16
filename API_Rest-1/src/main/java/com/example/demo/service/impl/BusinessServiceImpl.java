package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Business;
import com.example.demo.entity.Servicio;
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
	
	
	private int getNumberOfFinishedServices(Business business) {
    	List<Servicio> businessServices = business.getServicioList();
    	List<Servicio> finishedServices  = new ArrayList<>();
    	
    	for (Servicio s : businessServices) {
			if(s.getFinished() == 1)
				finishedServices.add(s);
		}
    	return finishedServices.size();
    }	
	
	
	private int getNumberOfServices(Business business) {
    	List<Servicio> businessServices = business.getServicioList();
    	return businessServices.size();
    }

	@Override
	public Business getBusinessByStudentId(int id) {
		Student student = studentRepository.findById(id);
		String email = student.getEmail();
		List<Business> business = getAllBusiness();
		Business businessSelected = null;
		for (Business b : business) {
			if(b.getEmail().equalsIgnoreCase(email))
				return b;	
		}
		return null;
	}

	@Override
	public Business getBusinessByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}	

}
