package com.example.CalculateReward.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardDetails {
	private String transactionId;
	private double transactionAmount;
	private int points;
	private String transactionDate;
}
