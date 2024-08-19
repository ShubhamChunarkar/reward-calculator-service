package com.example.CalculateReward.controller;

import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.entity.Transaction;
import com.example.CalculateReward.exception.NoCustomerException;
import com.example.CalculateReward.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void testSaveCustomer() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setTransactionId(511);
		transaction.setCustomerId(1);
		transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
		transaction.setTransactionAmount(89);

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerId(1);
		mockCustomer.setCustomerName("John");
		mockCustomer.setListtransaction(Collections.singletonList(transaction));

		when(customerService.save(any(Customer.class))).thenReturn(mockCustomer);

		String jsonContent = "{" + "\"customerId\": 1," + "\"customerName\": \"John\"," + "\"listtransaction\": [" + "{"
				+ "\"transactionId\": 511," + "\"customerId\": 1,"
				+ "\"transactionDate\": \"2024-08-11T12:41:04.063Z\"," + "\"transactionAmount\": 89" + "}" + "]" + "}";

		mockMvc.perform(
				MockMvcRequestBuilders.post("/customers/save").contentType("application/json").content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("$.customerId").value(1))
				.andExpect(jsonPath("$.customerName").value("John"))
				.andExpect(jsonPath("$.listtransaction[0].transactionId").value(511));
	}

	@Test
	void testGetAllCustomer() throws Exception {
		Customer customer1 = new Customer();
		customer1.setCustomerId(1);
		customer1.setCustomerName("John Doe");

		Customer customer2 = new Customer();
		customer2.setCustomerId(2);
		customer2.setCustomerName("Jane Smith");

		List<Customer> mockCustomers = Arrays.asList(customer1, customer2);

		when(customerService.getAllCustomer()).thenReturn(mockCustomers);

		mockMvc.perform(get("/customers/getAllCustomer")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].customerId").value(1))
				.andExpect(jsonPath("$[0].customerName").value("John Doe"))
				.andExpect(jsonPath("$[1].customerId").value(2))
				.andExpect(jsonPath("$[1].customerName").value("Jane Smith"));
	}

	@Test
	void testGetCustomerById() throws Exception {

		int customerId = 1;
		Customer customer = new Customer(customerId, "John Doe", null);
		when(customerService.findById(customerId)).thenReturn(customer);

		mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customer_id}", customerId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
				.andExpect(jsonPath("$.customerName").value(customer.getCustomerName()));
	}

	@Test
	void testGetCustomerByIdNotFound() throws Exception {
		int customerId = 5;

		when(customerService.findById(customerId)).thenReturn(null);

		NoCustomerException exception = null;
		try {
			customerController.getCustomerById(customerId);
		} catch (NoCustomerException e) {
			exception = e;
		}
		assertEquals("Invalid / Missing customer Id " + customerId, exception.getMessage());
	}

}