package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Student extends User{
    private String name, surname;
    @ManyToOne
    @JoinColumn(name = "profesionalFamilyId")
    private ProFamily profesionalFamily;
    @OneToMany(mappedBy = "studentId")
    private List<Servicio> servicios; 
}
