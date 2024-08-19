package com.example.CalculateReward.model;

import java.util.List;
import lombok.Data;

@Data
public class Rewards {
	private CustomerInfo customer;
	private long totalPoints;
	private List<RewardDetails> rewards;

}
