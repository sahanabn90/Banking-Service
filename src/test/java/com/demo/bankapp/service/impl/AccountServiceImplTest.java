package com.demo.bankapp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.AccountStatement;
import com.demo.bankapp.entity.Transaction;
import com.demo.bankapp.entity.request.TransferBalanceRequest;
import com.demo.bankapp.exception.AccountNotFoundException;
import com.demo.bankapp.repository.AccountRepository;
import com.demo.bankapp.repository.TransactionRepository;

@SpringBootTest
public class AccountServiceImplTest {

	@InjectMocks
	AccountServiceImpl accountServiceImpl;

	@Mock
	AccountRepository accountRepository;

	@Mock
	TransactionRepository transactionRepository;

	static TransferBalanceRequest transferBalanceRequest = new TransferBalanceRequest();

	static Account fromaccount = new Account();
	static Account toaccount = new Account();
	static Transaction transaction = new Transaction();
	static List<Transaction> transactionList = new ArrayList<Transaction>();

	@BeforeAll
	public static void setup() {

		fromaccount.setAccountNumber("2000000000");
		fromaccount.setCurrentBalance(10000);

		toaccount.setAccountNumber("2000000001");
		toaccount.setCurrentBalance(10000);

		transferBalanceRequest.setFromAccountNumber(fromaccount.getAccountNumber());
		transferBalanceRequest.setToAccountNumber(toaccount.getAccountNumber());
		transferBalanceRequest.setAmount(100);

		// amount = transferBalanceRequest.getAmount();
		transaction.setAccountNumber(transferBalanceRequest.getFromAccountNumber());
		transaction.setTransactionAmount(transferBalanceRequest.getAmount());
		transaction.setTransactionDateTime(new Timestamp(System.currentTimeMillis()));
		transactionList.add(transaction);
	}

	@Test
	@ExceptionHandler(AccountNotFoundException.class)
	public void testSendMoneyForSuccess() throws AccountNotFoundException {
		Mockito.when(accountRepository.getAccountByAccountNumber(fromaccount.getAccountNumber()))
				.thenReturn(fromaccount);
		Mockito.when(accountRepository.getAccountByAccountNumber(toaccount.getAccountNumber())).thenReturn(toaccount);
		System.out.println("to acc num balance:: " + fromaccount.getCurrentBalance());
		Mockito.when(accountRepository.save(fromaccount)).thenReturn(fromaccount);
		Mockito.when(accountRepository.save(toaccount)).thenReturn(toaccount);

		transaction = accountServiceImpl.sendMoney(transferBalanceRequest);
		System.out.println("Transaction:::" + transaction);
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

		Assertions.assertEquals(10100, toaccount.getCurrentBalance());
		Assertions.assertEquals(9900, fromaccount.getCurrentBalance());

	}

	@Test
	public void testGetStatementForSuccess() throws AccountNotFoundException {

		Mockito.when(accountRepository.getAccountByAccountNumber("2000000000")).thenReturn(fromaccount);
		Mockito.when(transactionRepository.findByAccountNumberEquals("2000000000")).thenReturn(transactionList);
		AccountStatement statement = accountServiceImpl.getStatement("2000000000");
		Assertions.assertNotNull(statement);
		Assertions.assertEquals(1, statement.getTransactionHistory().size());
	}

	@Test
	@ExceptionHandler(AccountNotFoundException.class)
	public void testGetStatementForException() throws AccountNotFoundException {

		Mockito.when(accountRepository.getAccountByAccountNumber("2000000000")).thenReturn(fromaccount);
		Mockito.when(transactionRepository.findByAccountNumberEquals("2000000000")).thenReturn(transactionList);

		Assertions.assertThrows(AccountNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				AccountStatement statement = accountServiceImpl.getStatement("2000000002");
				Assertions.assertNull(statement);
			}
		});
	}

	@Test
	public void testGetStatementForAnyAccountNumber() throws AccountNotFoundException {

		Mockito.when(accountRepository.getAccountByAccountNumber(Mockito.anyString())).thenReturn(fromaccount);
		AccountStatement statement = accountServiceImpl.getStatement("2000000002");

		Assertions.assertNotNull(statement);
	}
}
