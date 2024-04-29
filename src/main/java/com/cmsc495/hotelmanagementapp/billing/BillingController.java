package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: BillingController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/BillingController.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-29 
 * Description: This controller manages the operations related to billings in the hotel billing system.
 *              It provides mappings for creating, updating, and deleting billings data of customers, 
 *              as well as retrieving information about all billings.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillingController {

	@Autowired
	private BillingService billingService;
	
	/* The value="billing" will map to the <a href="billing"> tag in html -> the billing management button 
	 * This method retrieves billing data from the database and stores in the model for rendering in the view */
	@GetMapping("/billing")
	public String getAllBillings(Model model) {
		List<Billing> billings = billingService.getAllBillings();
		model.addAttribute("billings", billings);
		return "billing";
	}
}
