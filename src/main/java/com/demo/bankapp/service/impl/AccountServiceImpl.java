package com.demo.bankapp.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.AccountStatement;
import com.demo.bankapp.entity.Transaction;
import com.demo.bankapp.entity.request.AccountStatementRequest;
import com.demo.bankapp.entity.request.TransferBalanceRequest;
import com.demo.bankapp.exception.AccountNotFoundException;
import com.demo.bankapp.repository.AccountRepository;
import com.demo.bankapp.repository.TransactionRepository;
import com.demo.bankapp.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Transaction sendMoney(TransferBalanceRequest transferBalanceRequest) throws AccountNotFoundException {
		String fromAccountNum = transferBalanceRequest.getFromAccountNumber();
		System.out.println("from account num::" + fromAccountNum);
		String toAccountNum = transferBalanceRequest.getToAccountNumber();
		System.out.println("To account num::" + toAccountNum);
		double amount = transferBalanceRequest.getAmount();
		System.out.println("Balance::" + amount);

		Account fromAccount = accountRepository.getAccountByAccountNumber(fromAccountNum);
		if (fromAccount == null) {
			throw new AccountNotFoundException("Account not found");
		}
		System.out.println("from account*********" + fromAccount.getAccountNumber());

		System.out.println("fromAccount::" + fromAccount);
		Account toAccount = accountRepository.getAccountByAccountNumber(toAccountNum);
		System.out.println("to account*********" + toAccount.getAccountNumber());
		
		if (fromAccount.getCurrentBalance() > 1 && amount <= fromAccount.getCurrentBalance()) {
			System.out.println("inside if");
			fromAccount.setCurrentBalance(fromAccount.getCurrentBalance() - amount);
			accountRepository.save(fromAccount);
			toAccount.setCurrentBalance(toAccount.getCurrentBalance() + amount);
			accountRepository.save(toAccount);
			Transaction transaction = transactionRepository
					.save(new Transaction(fromAccountNum, amount, new Timestamp(System.currentTimeMillis())));
			return transaction;
		}

		return null;
	}

	@Override
	public AccountStatement getStatement(String accountNumber) throws AccountNotFoundException {
		Account account = accountRepository.getAccountByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException("Account not found");
		} else {

			return new AccountStatement(account.getCurrentBalance(),
					transactionRepository.findByAccountNumberEquals(accountNumber));
		}
	}

}
