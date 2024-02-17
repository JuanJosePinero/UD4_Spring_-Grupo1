package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.Business;
import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Servicio;
import com.example.demo.model.ServicioModel;

public interface ServicioService {
	
	public ServicioDTO addServicio(ServicioModel servicioModel);
	
	int deleteServicio(int id);
	
	Servicio updateServicio(ServicioModel servicio);
	
	ServicioModel getServicioById(int serviceId);
	
	public List<ServicioDTO> getServicesByBusinessId(Business business);
	
	List<ServicioModel> findServiciosByProFamily(String familyName);
	
	List<ServicioModel> getAllServicios();
	
	List<ServicioModel> getFilteredServices(String opcion, String filterBy);
	
	List<ServicioDTO>getServicesByBusinessIdAndProFamily(Business business,ProFamily profam);

	Servicio getOneServiceByBusinessId(Business business, String title, String description);
}
