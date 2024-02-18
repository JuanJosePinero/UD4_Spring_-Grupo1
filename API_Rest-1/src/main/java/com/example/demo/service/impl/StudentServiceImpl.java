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
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Servicio;
import com.example.demo.entity.Student;
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

	@Override
	public Student getStudentByName(String name) {
		Student student = studentRepository.findByName(name);
		return student;
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

//	Alumnos: recuperan todos los servicios correspondientes a su familia profesional.
	@Override
	public List<ServicioDTO> getServiceByStudentProfesionalFamily(int id) {
		Student student = getStudentById(id);
		List<Servicio> servicios = servicioRepository.findByProfesionalFamilyId(student.getProfesionalFamily());
		return servicioConverter.transform2DTO(servicios);
	}

//	Alumnos: recuperan todos los servicios correspondientes a su familia profesional, que tiene asignados
	@Override
	public List<ServicioDTO> getAssignedServiceByStudentProfesionalFamily(int id) {
		Student student = studentRepository.findById(id);
		List<Servicio> servicioLista = new ArrayList<>();

		// Verificar si el estudiante existe y tiene una familia profesional asignada
		if (student != null && student.getProfesionalFamily() != null) {
			List<Servicio> services = servicioRepository.findByProfesionalFamilyId(student.getProfesionalFamily());
			for (Servicio s : services) {
				// Verificar si el servicio tiene un estudiante asignado y si coincide con el id
				// del estudiante
				if (s.getStudentId() != null && s.getStudentId().getId() == student.getId()) {
					servicioLista.add(s);
				}
			}
		} else {
			return null;
		}

		return servicioConverter.transform2DTO(servicioLista);
	}

//	Alumnos: recuperan todos los servicios correspondientes a su familia profesional, que no tienen asignados ningún alumno
	@Override
	public List<ServicioDTO> getUnassignedServiceByStudentProfesionalFamily(int id) {
		Student student = studentRepository.findById(id);
		List<Servicio> servicioLista = new ArrayList<>();
		List<Servicio> services = servicioRepository.findByProfesionalFamilyId(student.getProfesionalFamily());
		for (Servicio s : services) {
			if (s.getStudentId() == null)
				servicioLista.add(s);
		}
		return servicioConverter.transform2DTO(servicioLista);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDTO> getAllStudentByEmail(String email) {
		List<Student> student = studentRepository.findStudentByEmail(email);
		if (!student.isEmpty()) {
			return null;
		} else {
			return studentConverter.transform(student);
		}
	}

}