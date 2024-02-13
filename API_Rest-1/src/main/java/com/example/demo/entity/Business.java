package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Business{
	@Id
	@GeneratedValue
	private int id;
	private String name, address,phone, logo;
	@OneToMany(mappedBy="businessId")
	private List<Servicio> servicioList;
	
	
	@ManyToOne
    @JoinColumn(name = "businessId")
	private User userId;
}
