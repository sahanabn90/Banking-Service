package com.demo.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.entity.AccountStatement;
import com.demo.bankapp.entity.Transaction;
import com.demo.bankapp.entity.request.AccountStatementRequest;
import com.demo.bankapp.entity.request.TransferBalanceRequest;
import com.demo.bankapp.exception.AccountNotFoundException;
import com.demo.bankapp.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	Environment environment;

	@Autowired
	AccountService accountService;
	
	@GetMapping("/port")
	public String getPortNo() {
		String port = environment.getProperty("local.server.port");
		return "From Bank app : " + port;
	}

	@PostMapping("/sendMoney")
	public Transaction sendMoney(@RequestBody TransferBalanceRequest transferBalanceRequest) throws AccountNotFoundException {

		return accountService.sendMoney(transferBalanceRequest);

	}
	
	@GetMapping("/getStatement/{accountNumber}")
	public AccountStatement getStatement(@PathVariable String accountNumber) throws AccountNotFoundException {
		return accountService.getStatement(accountNumber);
	}

}
