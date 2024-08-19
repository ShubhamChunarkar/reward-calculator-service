package com.example.CalculateReward.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.CalculateReward.constants.Constant;
import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.entity.Transaction;
import com.example.CalculateReward.model.Rewards;
import com.example.CalculateReward.repository.CustomerRepository;
import com.example.CalculateReward.repository.TransactionRepository;

public class RewardsServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardsServiceImpl rewardsServiceImpl;

	private Customer mockCustomer;
	private List<Transaction> mockTransactions;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockCustomer = new Customer();
		mockCustomer.setCustomerId(1);
		mockCustomer.setCustomerName("John Doe");

		Transaction transaction1 = new Transaction(1L, 1, Timestamp.from(Instant.now().minus(10, ChronoUnit.DAYS)),
				120.0);
		Transaction transaction2 = new Transaction(2L, 1, Timestamp.from(Instant.now().minus(15, ChronoUnit.DAYS)),
				170.0);
		Transaction transaction3 = new Transaction(3L, 1, Timestamp.from(Instant.now().minus(20, ChronoUnit.DAYS)),
				100.0);
		Transaction transaction4 = new Transaction(4L, 1, Timestamp.from(Instant.now().minus(25, ChronoUnit.DAYS)),
				101.0);

		mockTransactions = Arrays.asList(transaction1, transaction2, transaction3, transaction4);
	}

	@Test
	public void testGetRewardsByCustomerId() {

		when(customerRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(mockCustomer));
		when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(anyInt(), any(), any()))
				.thenReturn(mockTransactions);

		Rewards rewards = rewardsServiceImpl.getRewardsByCustomerId(1);

		assertEquals(1, rewards.getCustomer().getId());
		assertEquals("John Doe", rewards.getCustomer().getName());
		assertEquals(382, rewards.getTotalPoints());
		assertEquals(4, rewards.getRewards().size());
	}

	@Test
	public void testGetRewardsByCustomerId_NoTransactions() {

		when(customerRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(mockCustomer));
		when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(anyInt(), any(), any()))
				.thenReturn(Collections.emptyList());

		Rewards rewards = rewardsServiceImpl.getRewardsByCustomerId(1);

		assertEquals(1, rewards.getCustomer().getId());
		assertEquals("John Doe", rewards.getCustomer().getName());
		assertEquals(0, rewards.getTotalPoints());
		assertEquals(0, rewards.getRewards().size());
	}

	@Test
	public void testCalculateRewardForTransaction_BelowThreshold() {

		Transaction lowTransaction = new Transaction(5L, 1, Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS)),
				40.0);

		int points = rewardsServiceImpl.calculateRewardForTransaction(lowTransaction);

		assertEquals(0, points);
	}

	@Test
	public void testCalculateRewardForTransaction_AboveSecondLimit() {

		Transaction highTransaction = new Transaction(6L, 1, Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS)),
				150.0);

		int points = rewardsServiceImpl.calculateRewardForTransaction(highTransaction);

		assertEquals(150 - Constant.secondRewardLimit + (int) (50 * 2), points);
	}
}
