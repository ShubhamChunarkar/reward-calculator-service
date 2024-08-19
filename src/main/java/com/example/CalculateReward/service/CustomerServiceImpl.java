package com.example.CalculateReward.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.entity.Transaction;
import com.example.CalculateReward.exception.NoCustomerException;
import com.example.CalculateReward.repository.CustomerRepository;
import com.example.CalculateReward.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	private long transactionId;

	@Override
	public Customer save(Customer customer) {
		List<Transaction> transactionData = customer.getListtransaction();

		for (Transaction t : transactionData) {
			transactionId = t.getTransactionId();
		}
		if (transactionRepository.existsByTransactionId(transactionId)
				|| customerRepository.existsByCustomerId(customer.getCustomerId())) {
			log.error("already exists: ");
			throw new NoCustomerException("Already exists ");
		} else {
			return customerRepository.save(customer);
		}

	}

	public Customer findById(int customerId) {
		return customerRepository.findByCustomerId(customerId);
	}

	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

}
