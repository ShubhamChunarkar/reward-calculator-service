package com.example.CalculateReward;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import com.example.CalculateReward.controller.CustomerController;
import com.example.CalculateReward.model.Rewards;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@WebMvcTest(controllers = { CustomerController.class })
class CalculateRewardApplicationTests {

	@Autowired
	MockBean mockBean;
	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	CustomerController customerController;

	@Test
	void contextLoads() {
		assertThat(customerController).isNotNull();
		assertThat(
				this.restTemplate.getForObject("http://localhost" + port + "/customers/1001/rewards", Rewards.class));

	}

}
