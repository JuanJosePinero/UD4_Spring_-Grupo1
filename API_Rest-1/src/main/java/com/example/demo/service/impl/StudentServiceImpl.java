package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.converter.ServicioConverter;
import com.example.demo.converter.StudentConverter;
import com.example.demo.dto.ServicioDTO;
import com.example.demo.entity.Servicio;
import com.example.demo.entity.Student;
import com.example.demo.model.ServicioModel;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.ProFamilyRepository;
import com.example.demo.repository.ServicioRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;

@Configuration
@Service("studentService")
public class StudentServiceImpl implements StudentService, UserDetailsService {

	@Autowired
	@Qualifier("studentRepository")
	private StudentRepository studentRepository;
	
	@Autowired
	@Qualifier("proFamilyRepository")
	private ProFamilyRepository proFamilyRepository;
	
	@Autowired
	@Qualifier("servicioRepository")
	private ServicioRepository servicioRepository;
	
	@Autowired
	@Qualifier("servicioService")
	private ServicioServiceImpl servicioService;
	
	@Autowired
	@Qualifier("studentConverter")
	private StudentConverter studentConverter;
	
	@Autowired
	@Qualifier("servicioConverter")
	private ServicioConverter servicioConverter;

	public Student model2entity(StudentModel studentModel) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(studentModel, Student.class);
	}
//	@Override
//	public StudentModel entity2model(Student student) {
//	    ModelMapper mapper = new ModelMapper();
//	    mapper.createTypeMap(Student.class, StudentModel.class)
//	            .addMapping(src -> ((Student) src).getEnabled(), StudentModel::setEnabled);
//	    StudentModel studentModel = mapper.map(student, StudentModel.class);
//	    return studentModel;
//	}
	
//	@Override
//	public Student register(StudentModel studentModel) {
//		
//	}
	
//	@Override
//	public Student getStudentByEmail(String email) {
//	    return studentRepository.findByEmail(email)
//	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//	}
	
	@Override
	public StudentModel getStudentByName(String name) {
		Student student=studentRepository.findByName(name);
		return entity2model(student);
	}

	@Override
	public List<Student> listAllStudents() {
	    List<Student> students = new ArrayList<>();
	    for (Student s : studentRepository.findAll()) {
	        students.add(s);
	    }
	    return students;
	}

	@Override
	public Student getStudentById(int id) {
		Student student = studentRepository.findById(id);
		return student;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------
//	Alumnos: recuperan todos los servicios correspondientes a su familia profesional.
	@Override
	public List<ServicioDTO> getServiceByStudentProfesionalFamily(int id) {
	    Student student = getStudentById(id);
	    List<Servicio> servicios = servicioRepository.findByProfesionalFamilyId(student.getProfesionalFamily());
	    return servicioConverter.transform2DTO(servicios);
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------
//	Alumnos: recuperan todos los servicios correspondientes a su familia profesional, que tiene asignados
	@Override
	public List<ServicioModel> getAssignedServiceByStudentProfesionalFamily(int id) {
		Student student=studentRepository.findById(id);
		List<ServicioModel>servicioLista=new ArrayList<>();
		List<Servicio>services=servicioRepository.findByProfesionalFamilyId(student.getProfesionalFamily());
		for(Servicio s: services) {
			if(s.getStudentId().getId() == student.getId())
				servicioLista.add(servicioService.entity2model(s));
		}

		return servicioLista;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------
//	Alumnos: recuperan todos los servicios correspondientes a su familia profesional, que no tienen asignados ningún alumno
	@Override
	public List<ServicioModel> getUnassignedServiceByStudentProfesionalFamily(int id) {
		Student student=studentRepository.findById(id);
		List<ServicioModel>servicioLista=new ArrayList<>();
		List<Servicio>services=servicioRepository.findByProfesionalFamilyId(student.getProfesionalFamily());
		for(Servicio s: services) {
			if(s.getStudentId() == null)
			servicioLista.add(servicioService.entity2model(s));
		}
		return servicioLista;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentModel entity2model(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		com.example.demo.entity.Student student = studentRepository.findByUsername(username);
//		UserBuilder builder = null;
//		
//		if (student != null) {
//			if (student.getEnabled() == 0) {
//				return User.withUsername(student.getName())
//	                    .disabled(true)
//	                    .password(student.getPassword())
//	                    .authorities(new SimpleGrantedAuthority(student.getRole()))
//	                    .build();
//	        
//	        }
//			builder = User.withUsername(student.getName());
//			builder.disabled(false);
//			builder.password(student.getPassword());
//			builder.authorities(new SimpleGrantedAuthority(student.getRole()));
//		} else
//			throw new UsernameNotFoundException("Student not found or account is not activated");
//		return builder.build();
//	}


//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Override
//	public Student getStudentByUsername(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
//	
//	
//	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
//		com.example.demo.entity.Student student = studentRepository.findByUsername(email);
//		UserBuilder builder = null;
//
//		if (student != null) {
//			builder = User.withUsername(email);
//			builder.disabled(false);
//			builder.password(student.getPassword());
//			builder.authorities(new SimpleGrantedAuthority(student.getRole()));
//		} else
//			throw new UsernameNotFoundException("Student not found");
//		return builder.build();
//	}
	
}



