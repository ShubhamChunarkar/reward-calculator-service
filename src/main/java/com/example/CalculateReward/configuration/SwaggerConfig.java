package com.example.CalculateReward.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI rewardCalculatorOpenAPI() {
		return new OpenAPI().info(new Info().title("Calculate Reward API")
				.description("Reward Calculator application to calculate rewards of each customer based on date"));
	}

}
