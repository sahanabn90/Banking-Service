package com.demo.bankapp.service;

import org.springframework.http.ResponseEntity;

import com.demo.bankapp.entity.Customer;

public interface CustomerService {
	
	public ResponseEntity<Customer> createCustomer(Customer customer);

}
