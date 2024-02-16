package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.Business;
import com.example.demo.entity.ProFamily;
import com.example.demo.entity.Student;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTO {
	private int id;
	private String title;
	private String description;
	private Date registerDate;
	private Date happeningDate;
	@Nullable
	private Student studentId;
	private Business businessId;
	private ProFamily profesionalFamilyId;
	private int finished;
	private String comment;
}
