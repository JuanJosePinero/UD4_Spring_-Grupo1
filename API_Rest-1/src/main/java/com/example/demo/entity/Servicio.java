package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private Date registerDate;
    private Date happeningDate;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student studentId;
    @ManyToOne
    @JoinColumn(name = "businessId")
    private Business businessId;
    @ManyToOne
    @JoinColumn(name = "profesionalFamilyId")
    private ProFamily profesionalFamilyId;
    private float valoration;
    private int finished;
    private String comment;
    private int deleted;
}
