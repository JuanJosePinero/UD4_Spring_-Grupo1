package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Student;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Serializable> {

	Student findById(int id);

	public abstract Student findBySurname(String surname);

	public abstract Student findByEmail(String email);

	Student findByName(String name);

	List<Student> findByProfesionalFamily(ProFamily proFamily);

}