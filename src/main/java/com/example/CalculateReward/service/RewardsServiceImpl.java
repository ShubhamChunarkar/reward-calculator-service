package com.example.CalculateReward.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CalculateReward.constants.Constant;
import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.entity.Transaction;
import com.example.CalculateReward.model.CustomerInfo;
import com.example.CalculateReward.model.RewardDetails;
import com.example.CalculateReward.model.Rewards;
import com.example.CalculateReward.repository.CustomerRepository;
import com.example.CalculateReward.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RewardsServiceImpl implements RewardService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Rewards getRewardsByCustomerId(int customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Timestamp currentTimestamp = Timestamp.from(Instant.now());
		Timestamp lastMonthTimestamp = getDays(Constant.daysInMonths);
		Timestamp secondLastMonthTimestamp = getDays(2 * Constant.daysInMonths);
		Timestamp thirdLastMonthTimestamp = getDays(3 * Constant.daysInMonths);

		List<Transaction> allTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, thirdLastMonthTimestamp, currentTimestamp);

		List<RewardDetails> rewardDetailsList = allTransactions.stream().map(this::mapToRewardDetails)
				.collect(Collectors.toList());

		long totalPoints = rewardDetailsList.stream().mapToLong(RewardDetails::getPoints).sum();

		Rewards rewards = new Rewards();
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(customerId);
		customerInfo.setName(customer.getCustomerName());

		rewards.setCustomer(customerInfo);
		rewards.setTotalPoints(totalPoints);
		rewards.setRewards(rewardDetailsList);

		log.info("Total rewards calculated for customer {}: {}", customerId, totalPoints);

		return rewards;
	}

	@Override
	public List<Rewards> getAllCustomerRewards() {
		List<Customer> allCustomers = customerRepository.findAll();

		return allCustomers.stream().map(customer -> {
			Timestamp currentTimestamp = Timestamp.from(Instant.now());
			Timestamp thirdLastMonthTimestamp = getDays(3 * Constant.daysInMonths);

			List<Transaction> allTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
					customer.getCustomerId(), thirdLastMonthTimestamp, currentTimestamp);

			List<RewardDetails> rewardDetailsList = allTransactions.stream().map(this::mapToRewardDetails)
					.collect(Collectors.toList());

			long totalPoints = rewardDetailsList.stream().mapToLong(RewardDetails::getPoints).sum();

			Rewards rewards = new Rewards();
			CustomerInfo customerInfo = new CustomerInfo();
			customerInfo.setId(customer.getCustomerId());
			customerInfo.setName(customer.getCustomerName());

			rewards.setCustomer(customerInfo);
			rewards.setTotalPoints(totalPoints);
			rewards.setRewards(rewardDetailsList);

			return rewards;
		}).collect(Collectors.toList());
	}

	public RewardDetails mapToRewardDetails(Transaction transaction) {
		RewardDetails rewardDetails = new RewardDetails();
		rewardDetails.setTransactionId(String.valueOf(transaction.getTransactionId()));
		rewardDetails.setTransactionAmount(transaction.getTransactionAmount());
		rewardDetails.setPoints(calculateRewardForTransaction(transaction));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
		rewardDetails.setTransactionDate(transaction.getTransactionDate().toLocalDateTime().format(formatter));

		return rewardDetails;
	}

	public int calculateRewardForTransaction(Transaction transaction) {
		double amount = transaction.getTransactionAmount();
		if (amount > Constant.secondRewardLimit) {
			return (int) ((amount - Constant.secondRewardLimit) * 2
					+ (Constant.secondRewardLimit - Constant.firstRewardLimit));
		} else if (amount > Constant.firstRewardLimit) {
			return (int) (amount - Constant.firstRewardLimit);
		}
		return 0;
	}

	public Timestamp getDays(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}
}
