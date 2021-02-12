package com.demo.bankapp.controller;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.Customer;
import com.demo.bankapp.service.CustomerService;

@SpringBootTest
public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

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
	public void testCreateCustomerSuccess() {
		ResponseEntity<Customer> r = new ResponseEntity<>(HttpStatus.ACCEPTED);
		Mockito.when(customerService.createCustomer(customer)).thenReturn(r);
		ResponseEntity<Customer> cust = customerController.createCustomer(customer);
		Assertions.assertNotNull(cust);

	}

}
