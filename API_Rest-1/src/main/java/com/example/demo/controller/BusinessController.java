package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Business;
import com.example.demo.entity.Student;
import com.example.demo.model.BusinessModel;
import com.example.demo.model.StudentModel;
import com.example.demo.service.BusinessService;
import com.example.demo.service.ProFamilyService;
//import com.example.demo.service.ReportService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;
import com.example.demo.service.impl.ServicioServiceImpl;

@Controller
@RequestMapping("business")
public class BusinessController {

	private static final String ADD_BUSINESS_VIEW = "admin/addBusiness";
	private static final String BUSINESS_HOME_VIEW = "/business/businessHome";

	@Autowired
	@Qualifier("businessService")
	private BusinessService businessService;

//	@Autowired
//	@Qualifier("reportService")
//	private ReportService reportService;

	@Autowired
	@Qualifier("servicioService")
	private ServicioService servicioService;
	
	@Autowired
	@Qualifier("servicioService")
	private ServicioServiceImpl servicioServiceImpl;
	
	@Autowired
	@Qualifier("proFamilyService")
	private ProFamilyService proFamilyService;

	@Autowired
	@Qualifier("studentService")
	private StudentService studentService;




	@GetMapping("/addBusiness")
	public String addBusiness(Model model) {
		model.addAttribute("businessModel", new BusinessModel());
		List<Student> studentEmails = studentService.listAllStudents();
		model.addAttribute("studentEmails", studentEmails);
		return ADD_BUSINESS_VIEW;
	}

	@GetMapping("/home")
    public String getBusiness(@RequestParam(name="opcion", required=false, defaultValue="0") String opcion, 
                              @RequestParam(name="filterBy", required=false, defaultValue="null") String filterBy,
                             
                              Model model) {
        return processBusiness(opcion, filterBy, model);
    }

    @PostMapping("/home")
    public String postBusiness(@RequestParam(name="opcion", required=false, defaultValue="0") String opcion, 
                               @RequestParam(name="filterBy", required=false, defaultValue="null") String filterBy,
                               Model model) {
        return processBusiness(opcion, filterBy, model);
    }

    private String processBusiness(String opcion, String filterBy,Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        StudentModel student = studentService.getStudentByName(username);
        String email = student.getUsername();
        Business business = businessService.getIdByUsername(email);
       
        
      
        return BUSINESS_HOME_VIEW;
    }

}
