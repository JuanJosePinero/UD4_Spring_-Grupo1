package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.Business;
import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Servicio;
import com.example.demo.model.ServicioModel;

public interface ServicioService {
	
	public ServicioModel addServicio(ServicioModel servicioModel);
	
	int deleteServicio(int id);
	
	Servicio updateServicio(ServicioModel servicio);
	
	ServicioModel getServicioById(int serviceId);
	
	public List<ServicioDTO> getServicesByBusinessId(Business business);
	
	List<ServicioModel> findServiciosByProFamily(String familyName);
	
	List<ServicioModel> getAllServicios();
	
	List<ServicioModel> getFilteredServices(String opcion, String filterBy);
	
	List<ServicioDTO>getServicesByBusinessIdAndProFamily(Business business,ProFamily profam);

	
//	Servicio rateServicio(int servicioId, float valoration);
	
//	Servicio commentServicio(int servicioId, String comment);
	
//	public List<ServicioModel> getFinishedServicios();

//	List<ServicioModel> getUnassignedServicios();

//	List<ServicioModel> getAssignedButUncompletedServices();


	
	
//	public abstract List<Report> getReportsForServicesByBusinessId(Business business);
	
	

//	Servicio finishServicio(int servicioId);

//	public List<ServicioModel> getFinishedServiciosByProFamily(String familyName);

//	List<ServicioModel> getUnassignedServiciosByProFamily(String familyName);

//	List<ServicioModel> getAssignedButUncompletedServiciosByProFamily(String familyName);

//	Report createReportByServicioId(int servicioId, String reportText, int serviceTime, int studentId);

//	Servicio assignStudent(int servicioId, int studentId);
	
//	List<ServicioModel>findByValorationIsNotNullAndBusinessIdAndProfesionalFamilyId(Business business,ProFamily profamily);
	
//	List<ServicioModel>findByFinishedAndStudentId(int id, Student student);

	
//	List<ServicioModel> getServicesByTwoDates(Date registerDateBegin,Date registerDateEnd);

}
