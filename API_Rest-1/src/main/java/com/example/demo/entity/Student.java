package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student{
	
	@Id
	@GeneratedValue
	private int id;
    private String name, surname, email, password;
    @ManyToOne
    @JoinColumn(name = "profesionalFamilyId")
    private ProFamily profesionalFamily;
    @OneToMany(mappedBy = "studentId")
    private List<Servicio> servicios;
    @OneToOne(mappedBy = "studentID")
    private User userId;
       
}
