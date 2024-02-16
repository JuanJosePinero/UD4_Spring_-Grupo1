package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.example.demo.converter.ServicioConverter;
import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.Business;
import com.example.demo.entity.ProFamily;
//import com.example.demo.entity.Report;
import com.example.demo.entity.Servicio;
import com.example.demo.model.ServicioModel;
//import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.ServicioRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.ProFamilyService;
import com.example.demo.service.ServicioService;

@Configuration
@Service("servicioService")
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    
    
//    @Autowired
//    private ReportRepository reportRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ProFamilyService proFamilyService;
    
    @Autowired
    private ServicioConverter servicioConverter;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }
    
    public Servicio model2entity(ServicioModel servicioModel) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(servicioModel, Servicio.class);
	}

	public ServicioModel entity2model(Servicio servicio) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(servicio, ServicioModel.class);
	}
	
	@Override
	public List<ServicioModel> getFilteredServices(String opcion, String filterBy) {
		List<ServicioModel> listServicios = new ArrayList<>();

		if (Integer.parseInt(opcion) != 0) {
	        ProFamily profam = proFamilyService.findById(Integer.parseInt(opcion));
	        String proFamName = profam.getName();
	        listServicios = findServiciosByProFamily(proFamName);
	    } else {
	        listServicios = getAllServicios();
	    }

	    return listServicios;
	}
	
	@Override
	public ServicioModel addServicio(ServicioModel servicioModel) {
	    Servicio servicio = model2entity(servicioModel);
	    servicio.setTitle(servicioModel.getTitle());
	    servicio.setDescription(servicioModel.getDescription());
	    servicio.setHappeningDate(servicioModel.getHappeningDate());
	    servicio.setRegisterDate(servicioModel.getRegisterDate());
	    servicio.setId(servicioModel.getId());
	    servicio.setBusinessId(servicioModel.getBusinessId());
	    servicio.setProfesionalFamilyId(servicioModel.getProfesionalFamilyId());
	    servicio.setStudentId(servicioModel.getStudentId());
	    servicio.setValoration(0);
	    servicio.setComment(null);
	    servicio.setDeleted(0);
	    servicio.setFinished(0);
	    
	    servicio = servicioRepository.save(servicio);

	    ServicioModel createdServicio = entity2model(servicio);

	    return createdServicio;    
	}


	@Override
	public int deleteServicio(int id) {
		Servicio servicio = servicioRepository.findById(id);

        if (servicio.getDeleted() == 0) {
        	servicio.setDeleted(1);
        	servicioRepository.save(servicio);
            return 1;
        } else
            return 0;
	}

	@Override
	public Servicio updateServicio(ServicioModel servicioModel) {
	    Servicio servicio = servicioRepository.findById(servicioModel.getId());

	    if (servicio != null) {
	        if (servicioModel.getTitle() != null) {
	            servicio.setTitle(servicioModel.getTitle());
	        }

	        if (servicioModel.getDescription() != null) {
	            servicio.setDescription(servicioModel.getDescription());
	        }

	        if (servicioModel.getRegisterDate() != null) {
	            servicio.setRegisterDate(servicioModel.getRegisterDate());
	        }

	        // Considera -1 como un valor que indica que el campo no debe actualizarse
	        if (servicioModel.getFinished() != -1 || servicioModel.getFinished() != 0 || servicioModel.getFinished() != 1 ) {
	            servicio.setFinished(servicioModel.getFinished());
	        }

	        if (servicioModel.getComment() != null) {
	            servicio.setComment(servicioModel.getComment());
	        }

	        // Considera -1 como un valor que indica que el campo no debe actualizarse
	        if (servicioModel.getValoration() != -1 || servicioModel.getValoration() > 10 || servicioModel.getValoration() < 0) {
	            servicio.setValoration(servicioModel.getValoration());
	        }

	        servicio = servicioRepository.save(servicio);
	    }

	    return servicio;
	}


	@Override
	public List<ServicioModel> findServiciosByProFamily(String familyName) {
	    List<ServicioModel> servicioModels = new ArrayList<>();

	    for (Servicio servicio : servicioRepository.findAll()) {
	        if (servicio.getProfesionalFamilyId() != null && servicio.getProfesionalFamilyId().getName().equals(familyName)) {
	            servicioModels.add(entity2model(servicio));
	        }
	    }

	    return servicioModels;
	}

	@Override
	public List<ServicioModel> getAllServicios() {
	    List<Servicio> servicios = servicioRepository.findAll();
	    List<ServicioModel> servicioModels = new ArrayList<>();

	    for (Servicio servicio : servicios) {
	        ServicioModel servicioModel = entity2model(servicio);
	        servicioModels.add(servicioModel);
	    }

	    return servicioModels;
	}

	@Override
	public List<ServicioDTO> getServicesByBusinessId(Business business) {
		ServicioConverter converter = new ServicioConverter();

		List<Servicio> servicios =servicioRepository.findAll();
		List<ServicioDTO> servicesDTO = new ArrayList<>();
		for(Servicio servicio: servicios) {
			if(servicio.getBusinessId()!=null && servicio.getBusinessId().getId() == business.getId()) {
				servicesDTO.add(converter.transform(servicio));
			}
		}
		return servicesDTO;
		
	}

	@Override
	public ServicioModel getServicioById(int serviceId) {
	    Servicio servicio = servicioRepository.findById(serviceId);
	    return servicio != null ? entity2model(servicio) : null;
	}

	public List<ProFamily> getProfessionalFamiliesByBusinessId(List<Servicio>listServices){
		List<ProFamily>reports=new ArrayList<>();
		for(Servicio servicio: listServices) {
			reports.add(servicio.getProfesionalFamilyId());
		}
		Collections.sort(reports, Comparator.comparing(ProFamily::getName));
		return reports;
	}

	@Override
	public List<ServicioDTO> getServicesByBusinessIdAndProFamily(Business business, ProFamily profam) {
		List<Servicio> servicios = servicioRepository.findByBusinessIdAndProfesionalFamilyId(business, profam);
		return servicioConverter.transform2DTO(servicios);
	}
}