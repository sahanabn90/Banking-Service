package com.demo.bankapp.service.impl;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.Customer;
import com.demo.bankapp.repository.CustomerRepository;
import com.demo.bankapp.service.AccountService;
import com.demo.bankapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public ResponseEntity<Customer> createCustomer(Customer customer) {

		Account account = new Account();
		Random random = new Random();
		int n = random.nextInt(10) + 1000000000;
		account.setAccountNumber(Integer.toString(n));
		// account.setAccountNumber(String.valueOf((Math.random() * 9 * Math.pow(10,
		// 15)) + Math.pow(10, 15)));

		account.setCurrentBalance(10000);
		customer.setAccount(account);
		customerRepository.save(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}

}
