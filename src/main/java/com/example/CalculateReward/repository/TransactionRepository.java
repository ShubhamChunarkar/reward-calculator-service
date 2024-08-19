package com.example.CalculateReward.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.CalculateReward.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findAllByCustomerId(Long customerId);

	boolean existsByTransactionId(Long transactionId);

	@Query(value = "select ea.customer_id,ea.amount,ea.transaction_date,ea.transaction_id from sys.transaction ea join sys.customer ae on ea.customer_id=ae.customer_id where ea.transaction_date >= :startDate and ea.transaction_date <= :endDate and ea.customer_id = :customer_id ", nativeQuery = true)
	public List<Transaction> findAllByCustomerIdAndTransactionDateBetween(@Param("customer_id") int customerId,
			@Param("startDate") Timestamp from, @Param("endDate") Timestamp to);

}
