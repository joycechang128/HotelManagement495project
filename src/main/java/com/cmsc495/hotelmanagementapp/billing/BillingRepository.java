package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: BillingRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/BillingRepository.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-04
 * Description: This repository interface provides methods for accessing and manipulating data in the database related to billings. 
 *              It defines CRUD operations for billing entities, allowing interaction with the underlying data store.
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {

	Billing save(Billing billing);
	
	Optional<Billing> findBillingByBillingId(int billingId);
	
	List<Billing> findAll();
	
	void deleteBillingByBillingId(int billingId);
	
	@Query("SELECT COUNT(b) FROM Billing b")
	int countTotalNumOfBillings();

	@Query("SELECT COUNT(b) FROM Billing b WHERE b.paymentStatus = 'Paid'")
	int countNumPaid();

	@Query("SELECT COUNT(b) FROM Billing b WHERE b.paymentStatus = 'Unpaid'")
	int countNumUnpaid();

	@Query("SELECT COUNT(b) FROM Billing b WHERE b.paymentStatus = 'Overdue'")
	int countNumOverdue();
}
