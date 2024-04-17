package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: BillingService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/BillingService.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Bryce Campbell
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingService {
	@Autowired
	private BillingRepository billingRepository;
	
	public List<Billing> getAllBillings() {
		return billingRepository.findAll();
	}
}
