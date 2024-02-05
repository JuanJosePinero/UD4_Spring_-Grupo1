package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Customer;
import com.example.demo.model.CustomerDTO;

public interface CustomerService {
	
	public abstract CustomerDTO addCustomer(CustomerDTO customerDTO);
	public abstract List<CustomerDTO> listAllCustomers();
	public abstract Customer findCustomerById(int id);
	public abstract CustomerDTO findCustomerByIdModel(int id);
	public abstract void removeCustomer(int id);
	public abstract Customer transform(CustomerDTO customerDTO);
	public abstract CustomerDTO transform(Customer customer);
	
	
	
	
}
