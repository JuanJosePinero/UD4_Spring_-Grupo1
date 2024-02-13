package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Business extends User{
	private String name, address,phone, logo;
	@OneToMany(mappedBy="businessId")
	private List<Servicio> servicioList;
}
