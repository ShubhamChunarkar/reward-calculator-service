package com.example.CalculateReward.entity;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "transaction")
public class Transaction {
	@Id
	@Column(name = "transaction_id")
	private long transactionId;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "transaction_date")
	private Timestamp transactionDate;

	@Column(name = "amount")
	private double transactionAmount;

}
