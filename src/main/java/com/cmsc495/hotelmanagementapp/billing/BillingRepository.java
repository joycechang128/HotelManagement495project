package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: BillingRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/BillingRepository.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Bryce Campbell
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {
	List<Billing> findAll();
}
