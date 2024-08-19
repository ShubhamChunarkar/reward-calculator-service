package com.example.CalculateReward.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerGlobalException {

	@ExceptionHandler
	public ResponseEntity<CustomerErrorRepsonse> handleException(NoCustomerException noCustomerException) {
		CustomerErrorRepsonse customerErrorRepsonse = new CustomerErrorRepsonse();
		customerErrorRepsonse.setMessageString(noCustomerException.getMessage());
		customerErrorRepsonse.setStatus(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerErrorRepsonse);
	}

}
