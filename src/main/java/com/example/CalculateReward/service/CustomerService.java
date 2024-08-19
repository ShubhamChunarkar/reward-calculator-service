package com.example.CalculateReward.service;

import java.util.List;
import com.example.CalculateReward.entity.Customer;

public interface CustomerService {
	public Customer save(Customer customer);

	public Customer findById(int customerId);

	public List<Customer> getAllCustomer();
}
