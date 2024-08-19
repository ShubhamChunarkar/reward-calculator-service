package com.example.CalculateReward.service;

import java.util.List;

import com.example.CalculateReward.model.Rewards;

public interface RewardService {
	public Rewards getRewardsByCustomerId(int customerId);

	public List<Rewards> getAllCustomerRewards();

}
