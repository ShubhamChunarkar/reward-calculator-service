package com.example.CalculateReward.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.exception.NoCustomerException;
import com.example.CalculateReward.model.Rewards;
import com.example.CalculateReward.repository.CustomerRepository;
import com.example.CalculateReward.service.RewardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "RewardController", description = "APIs to fetch customer rewards")
public class RewardController {

	@Autowired
	private RewardService rewardService;

	@Autowired
	private CustomerRepository customerRpository;

	@GetMapping(value = "/{customerId}/rewards")
	@Operation(summary = "Fetch rewards based on customerId")
	public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") int customerId) {

		Customer customer = customerRpository.findByCustomerId(customerId);
		if (customer == null) {
			log.error("Invalid id: " + customerId);
			throw new NoCustomerException("Invalid / Missing customer Id " + customerId);
		}
		Rewards customerRewards = rewardService.getRewardsByCustomerId(customerId);
		return new ResponseEntity<>(customerRewards, HttpStatus.OK);
	}

	@GetMapping("/getAllCustomer")
	@Operation(summary = "Fetch rewards of all customers")
	public ResponseEntity<List<Rewards>> getAllCustomerRewards() {
		List<Rewards> allCustomerRewards = rewardService.getAllCustomerRewards();
		return new ResponseEntity<>(allCustomerRewards, HttpStatus.OK);
	}
}
