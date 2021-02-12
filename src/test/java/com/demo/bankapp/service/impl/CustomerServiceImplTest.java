package com.demo.bankapp.service.impl;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.Customer;
import com.demo.bankapp.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceImplTest {
	
	@InjectMocks
	CustomerServiceImpl customerServiceImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	static Customer customer = new Customer();
	static Account account = new Account();

	@BeforeAll
	public static void setup() {
		Random random = new Random();
		int n = random.nextInt(10) + 2000000000;
		account.setAccountNumber(Integer.toString(n));
		account.setCurrentBalance(10000);
		customer.setAccount(account);
		customer.setEmail("test@gmail.com");
		customer.setFirstName("ftest");
		customer.setLastName("ltest");

	}
	
	@Test
	public void testCreateCustomerForSuccess() {
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		customerServiceImpl.createCustomer(customer);
		Assertions.assertNotNull(customer);
		Assertions.assertEquals("ftest",customer.getFirstName());
		System.out.println("Account::" +account.getAccountNumber());
		
	}


}
