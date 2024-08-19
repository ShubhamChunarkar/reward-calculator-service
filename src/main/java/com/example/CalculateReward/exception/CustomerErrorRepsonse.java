package com.example.CalculateReward.exception;

public class CustomerErrorRepsonse {
	int status;
	String messageString;

	public int getStatus() {

		return status;
	}

	public CustomerErrorRepsonse(int status, String messageString) {
		super();
		this.status = status;
		this.messageString = messageString;
	}

	public CustomerErrorRepsonse() {
		super();
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

}
