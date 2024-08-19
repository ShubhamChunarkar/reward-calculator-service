package com.example.CalculateReward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CalculateReward.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	public Customer findByCustomerId(int customerId);

	public boolean existsByCustomerId(int customerId);
}
