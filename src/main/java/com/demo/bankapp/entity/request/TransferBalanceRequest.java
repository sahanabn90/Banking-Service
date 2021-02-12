package com.demo.bankapp.entity.request;

public class TransferBalanceRequest {
	
	private String fromAccountNumber;
	private String toAccountNumber;
	private double amount;
	
	public TransferBalanceRequest() {
		// TODO Auto-generated constructor stub
	}

	public TransferBalanceRequest(String fromAccountNumber, String toAccountNumber, double amount) {
		super();
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
	}

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
