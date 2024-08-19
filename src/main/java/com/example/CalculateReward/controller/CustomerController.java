package com.example.CalculateReward.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.entity.Transaction;
import com.example.CalculateReward.exception.NoCustomerException;
import com.example.CalculateReward.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
@Tag(name = "CustomerController", description = "APIs to register customer details and transaction details")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/save")
	@Operation(summary = "register customer details and transaction details")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		List<Transaction> transactions = customer.getListtransaction();
		Customer customer2 = customer;
		customer2.setListtransaction(transactions);
		customerService.save(customer2);
		log.info("Customer added transaction details {}: {}");
		return new ResponseEntity<Customer>(customer2, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.GET)
	@Operation(summary = "To fetch all the customer details")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		List<Customer> customers = customerService.getAllCustomer();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{customer_id}", method = RequestMethod.GET)
	@Operation(summary = "Fetch customer details by ID")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int customer_id) {
		Customer customer = customerService.findById(customer_id);

		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			log.error("Invalid id: " + customer_id);
			throw new NoCustomerException("Invalid / Missing customer Id " + customer_id);
		}
	}

}
