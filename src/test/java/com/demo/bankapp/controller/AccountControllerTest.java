package com.demo.bankapp.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.bankapp.entity.Account;
import com.demo.bankapp.entity.AccountStatement;
import com.demo.bankapp.entity.Transaction;
import com.demo.bankapp.entity.request.TransferBalanceRequest;
import com.demo.bankapp.exception.AccountNotFoundException;
import com.demo.bankapp.service.AccountService;

@SpringBootTest
public class AccountControllerTest {

	@InjectMocks
	AccountController accountController;

	@Mock
	AccountService accountService;

	static TransferBalanceRequest transferBalanceRequest = new TransferBalanceRequest();

	static Account fromaccount = new Account();
	static Account toaccount = new Account();
	static Transaction transaction = new Transaction();
	static List<Transaction> transactionList = new ArrayList<Transaction>();
	static AccountStatement accountStatement = new AccountStatement();

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

		accountStatement.setCurrentBalance(fromaccount.getCurrentBalance());
		accountStatement.setTransactionHistory(transactionList);
	}

	@Test
	public void testSendMoneySuccess() throws AccountNotFoundException {
		Mockito.when(accountService.sendMoney(transferBalanceRequest)).thenReturn(transaction);
		Transaction transaction1 = accountController.sendMoney(transferBalanceRequest);
		assertNotNull(transaction1);
		Assertions.assertEquals(100, transaction1.getTransactionAmount());

	}

	@Test
	public void testGetStatementForSuccess() throws AccountNotFoundException {
		Mockito.when(accountService.getStatement("2000000000")).thenReturn(accountStatement);
		AccountStatement statement = accountController.getStatement("2000000000");
		assertNotNull(statement);
		Assertions.assertEquals(1, statement.getTransactionHistory().size());

	}

	@Test
	public void testGetStatementForTransactionListEmpty() throws AccountNotFoundException {
		Mockito.when(accountService.getStatement("2000000000")).thenReturn(accountStatement);
		AccountStatement statement = accountController.getStatement("2000000002");
		Assertions.assertNull(statement);

	}
	
	@Test
	public void testGetStatementForAnyAccountNum() throws AccountNotFoundException {
		Mockito.when(accountService.getStatement(Mockito.anyString())).thenReturn(accountStatement);
		AccountStatement statement = accountController.getStatement("2000000002");
		Assertions.assertNotNull(statement);
		Assertions.assertEquals(1, statement.getTransactionHistory().size());

	}


}
