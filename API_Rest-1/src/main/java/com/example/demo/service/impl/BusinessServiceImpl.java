package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Business;
import com.example.demo.entity.Servicio;
import com.example.demo.model.BusinessModel;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ServicioRepository;
import com.example.demo.service.BusinessService;

@Configuration
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	
	private final BusinessRepository businessRepository;
	
	@Autowired
	@Qualifier("servicioRepository")
	 private ServicioRepository servicioRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }
    
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
		Business business=businessRepository.findByUsername(username);
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

}
