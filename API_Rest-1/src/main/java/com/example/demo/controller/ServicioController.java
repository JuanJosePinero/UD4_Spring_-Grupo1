package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Business;
import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Servicio;
import com.example.demo.model.ServicioModel;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.ServicioRepository;
import com.example.demo.service.BusinessService;
import com.example.demo.service.ProFamilyService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.StudentService;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

	private static final String ADD_SERVICIO_VIEW = "business/addServicio";
	private static final String RATE_SERVICIO_VIEW = "business/rateServicio";
	private static final String COMMENT_SERVICIO_VIEW = "business/commentServicio";
	@Autowired
	@Qualifier("servicioService")
	 private ServicioService servicioService;

	@Autowired
	@Qualifier("studentService")
    private StudentService studentService;
	
	@Autowired
	@Qualifier("businessService")
    private BusinessService businessService;
	
	@Autowired
	@Qualifier("proFamilyService")
	 private ProFamilyService proFamilyService;
	
	@Autowired
	@Qualifier("servicioRepository")
	 private ServicioRepository servicioRepository;
	
	@GetMapping("/addServicio")
	public String addServicio(Model model) {
	    List<ProFamily> profesionalFamilies = proFamilyService.getAll();
	    model.addAttribute("servicioModel", new ServicioModel());
	    model.addAttribute("profesionalFamilies", profesionalFamilies);
	    return ADD_SERVICIO_VIEW;
	}

	@PostMapping("/addServicio")
	public String addServicioPost(@ModelAttribute ServicioModel servicioModel, Model model, RedirectAttributes redirectAttributes) {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = ((UserDetails) principal).getUsername();
	    StudentModel student = studentService.getStudentByName(username);
	    String email = student.getUsername();	    
	    Business business = businessService.getIdByUsername(email);
	    servicioModel.setBusinessId(business);
	    model.addAttribute("servicioModel", servicioModel);
	    model.addAttribute("business", business);
	    servicioService.addServicio(servicioModel);
	    redirectAttributes.addFlashAttribute("successMessage", "Service added correctly");
	    return "redirect:/business/home";
	}

	@PostMapping("/editServicio")
    public String saveEditedServicio(@ModelAttribute ServicioModel servicioModel, RedirectAttributes redirectAttributes) {
		servicioService.updateServicio(servicioModel);
		 redirectAttributes.addFlashAttribute("successMessage", "Service edited correctly");
        return "redirect:/business/home";
    }
	
	@PostMapping("/deleteServicio/{servicioId}")
	public String delete(@PathVariable("servicioId") int servicioId, Model model, RedirectAttributes redirectAttributes) {
		servicioService.deleteServicio(servicioId);
		redirectAttributes.addFlashAttribute("successMessage", "Service deleted correctly");
	    return "redirect:/business/home";
	}
	
}
