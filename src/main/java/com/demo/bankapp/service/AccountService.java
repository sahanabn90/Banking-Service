package com.demo.bankapp.service;

import org.springframework.http.ResponseEntity;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.AccountStatement;
import com.demo.bankapp.entity.Customer;
import com.demo.bankapp.entity.Transaction;
import com.demo.bankapp.entity.request.AccountStatementRequest;
import com.demo.bankapp.entity.request.TransferBalanceRequest;
import com.demo.bankapp.exception.AccountNotFoundException;

public interface AccountService {
	public Transaction sendMoney(TransferBalanceRequest transferBalanceRequest) throws AccountNotFoundException;
	public AccountStatement getStatement(String accountNumber) throws AccountNotFoundException;
}
