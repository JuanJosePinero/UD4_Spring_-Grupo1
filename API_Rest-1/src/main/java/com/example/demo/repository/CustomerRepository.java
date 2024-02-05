package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;

@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository <Customer, Serializable> {
	public abstract Customer findById(int id);

}
