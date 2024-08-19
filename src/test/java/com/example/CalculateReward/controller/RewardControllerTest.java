package com.example.CalculateReward.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.CalculateReward.entity.Customer;
import com.example.CalculateReward.model.CustomerInfo;
import com.example.CalculateReward.model.RewardDetails;
import com.example.CalculateReward.model.Rewards;
import com.example.CalculateReward.repository.CustomerRepository;
import com.example.CalculateReward.service.RewardService;

class RewardControllerTest {

	@Mock
	private RewardService rewardService;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardController rewardController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
	}

	@Test
	void testGetRewardsByCustomerId_Success() throws Exception {
		// Arrange
		int customerId = 1;
		Rewards rewards = new Rewards();
		rewards.setCustomer(new CustomerInfo(customerId, "John Doe"));
		rewards.setTotalPoints(100);

		List<RewardDetails> rewardDetailsList = new ArrayList<>();
		RewardDetails rewardDetails = new RewardDetails();
		rewardDetails.setTransactionId("12345");
		rewardDetails.setTransactionAmount(120.0);
		rewardDetails.setPoints(90);
		rewardDetails.setTransactionDate("2024-08-03T08:49:41.859753");
		rewardDetailsList.add(rewardDetails);

		rewards.setRewards(rewardDetailsList);
		when(customerRepository.findByCustomerId(customerId))
				.thenReturn(new Customer(customerId, "John Doe", new ArrayList<>()));
		when(rewardService.getRewardsByCustomerId(customerId)).thenReturn(rewards);

		mockMvc.perform(get("/api/v1/{customerId}/rewards", customerId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.customer.id").value(customerId))
				.andExpect(jsonPath("$.customer.name").value("John Doe"))
				.andExpect(jsonPath("$.totalPoints").value(100))
				.andExpect(jsonPath("$.rewards[0].transactionId").value("12345"))
				.andExpect(jsonPath("$.rewards[0].transactionAmount").value(120.0))
				.andExpect(jsonPath("$.rewards[0].points").value(90))
				.andExpect(jsonPath("$.rewards[0].transactionDate").value("2024-08-03T08:49:41.859753"));

	}

	@Test
	void testGetAllCustomerRewards_Success() throws Exception {
		Rewards rewards1 = new Rewards();
		rewards1.setCustomer(new CustomerInfo(1, "John"));
		rewards1.setTotalPoints(300);

		List<RewardDetails> rewards1List = Arrays.asList(
				new RewardDetails("501", 200.0, 250, "2024-08-11T17:47:00.722000"),
				new RewardDetails("502", 100.0, 50, "2024-07-11T17:47:00.722000"),
				new RewardDetails("503", 45.0, 0, "2024-07-26T17:47:00.722000"));
		rewards1.setRewards(rewards1List);

		Rewards rewards2 = new Rewards();
		rewards2.setCustomer(new CustomerInfo(2, "Shubham"));
		rewards2.setTotalPoints(300);

		List<RewardDetails> rewards2List = Arrays.asList(
				new RewardDetails("504", 200.0, 250, "2024-08-05T17:47:00.722000"),
				new RewardDetails("505", 100.0, 50, "2024-07-01T17:47:00.722000"));
		rewards2.setRewards(rewards2List);

		Rewards rewards3 = new Rewards();
		rewards3.setCustomer(new CustomerInfo(3, "Joseph"));
		rewards3.setTotalPoints(196);

		List<RewardDetails> rewards3List = Arrays.asList(
				new RewardDetails("511", 89.0, 39, "2024-08-11T18:11:04.063000"),
				new RewardDetails("512", 129.0, 108, "2024-07-11T18:11:04.063000"),
				new RewardDetails("513", 99.0, 49, "2024-08-01T18:11:04.063000"));
		rewards3.setRewards(rewards3List);

		List<Rewards> rewardsList = Arrays.asList(rewards1, rewards2, rewards3);

		when(rewardService.getAllCustomerRewards()).thenReturn(rewardsList);

		mockMvc.perform(get("/api/v1/getAllCustomer").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].customer.id").value(1))
				.andExpect(jsonPath("$[0].customer.name").value("John"))
				.andExpect(jsonPath("$[0].totalPoints").value(300))
				.andExpect(jsonPath("$[0].rewards[0].transactionId").value("501"))
				.andExpect(jsonPath("$[0].rewards[0].transactionAmount").value(200.0))
				.andExpect(jsonPath("$[0].rewards[0].points").value(250))
				.andExpect(jsonPath("$[0].rewards[0].transactionDate").value("2024-08-11T17:47:00.722000"))
				.andExpect(jsonPath("$[0].rewards[1].transactionId").value("502"))
				.andExpect(jsonPath("$[0].rewards[1].transactionAmount").value(100.0))
				.andExpect(jsonPath("$[0].rewards[1].points").value(50))
				.andExpect(jsonPath("$[0].rewards[1].transactionDate").value("2024-07-11T17:47:00.722000"))
				.andExpect(jsonPath("$[0].rewards[2].transactionId").value("503"))
				.andExpect(jsonPath("$[0].rewards[2].transactionAmount").value(45.0))
				.andExpect(jsonPath("$[0].rewards[2].points").value(0))
				.andExpect(jsonPath("$[0].rewards[2].transactionDate").value("2024-07-26T17:47:00.722000"))
				.andExpect(jsonPath("$[1].customer.id").value(2))
				.andExpect(jsonPath("$[1].customer.name").value("Shubham"))
				.andExpect(jsonPath("$[1].totalPoints").value(300))
				.andExpect(jsonPath("$[1].rewards[0].transactionId").value("504"))
				.andExpect(jsonPath("$[1].rewards[0].transactionAmount").value(200.0))
				.andExpect(jsonPath("$[1].rewards[0].points").value(250))
				.andExpect(jsonPath("$[1].rewards[0].transactionDate").value("2024-08-05T17:47:00.722000"))
				.andExpect(jsonPath("$[1].rewards[1].transactionId").value("505"))
				.andExpect(jsonPath("$[1].rewards[1].transactionAmount").value(100.0))
				.andExpect(jsonPath("$[1].rewards[1].points").value(50))
				.andExpect(jsonPath("$[1].rewards[1].transactionDate").value("2024-07-01T17:47:00.722000"))
				.andExpect(jsonPath("$[2].customer.id").value(3))
				.andExpect(jsonPath("$[2].customer.name").value("Joseph"))
				.andExpect(jsonPath("$[2].totalPoints").value(196))
				.andExpect(jsonPath("$[2].rewards[0].transactionId").value("511"))
				.andExpect(jsonPath("$[2].rewards[0].transactionAmount").value(89.0))
				.andExpect(jsonPath("$[2].rewards[0].points").value(39))
				.andExpect(jsonPath("$[2].rewards[0].transactionDate").value("2024-08-11T18:11:04.063000"))
				.andExpect(jsonPath("$[2].rewards[1].transactionId").value("512"))
				.andExpect(jsonPath("$[2].rewards[1].transactionAmount").value(129.0))
				.andExpect(jsonPath("$[2].rewards[1].points").value(108))
				.andExpect(jsonPath("$[2].rewards[1].transactionDate").value("2024-07-11T18:11:04.063000"))
				.andExpect(jsonPath("$[2].rewards[2].transactionId").value("513"))
				.andExpect(jsonPath("$[2].rewards[2].transactionAmount").value(99.0))
				.andExpect(jsonPath("$[2].rewards[2].points").value(49))
				.andExpect(jsonPath("$[2].rewards[2].transactionDate").value("2024-08-01T18:11:04.063000"));
	}

	@Test
	void testGetAllCustomerRewards_EmptyList() throws Exception {
		when(rewardService.getAllCustomerRewards()).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/api/v1/getAllCustomer").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
	}

	@Test
	void testGetRewardsByCustomerId_CustomerNotFound() throws Exception {
		int customerId = 5;
		when(customerRepository.findByCustomerId(customerId)).thenReturn(new Customer());

		mockMvc.perform(get("/api/v1/{customerId}/rewards", customerId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());

	}

}
