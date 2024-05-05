package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: BillingController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/BillingController.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-04 
 * Description: This controller manages the operations related to billings in the hotel billing system.
 *              It provides mappings for creating, updating, and deleting billings data of customers, 
 *              as well as retrieving information about all billings.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BillingController {

	@Autowired
	private BillingService billingService;
	
	@Autowired
	private BillingRepository billingRepository;
	
	/* The value="billing" will map to the <a href="billing"> tag in html -> the billing management button 
	 * This method retrieves billing data from the database and stores in the model for rendering in the view */
	@GetMapping("/billing")
	public String getAllBillings(Model model) {
		List<Billing> billings = billingService.getAllBillings();
		model.addAttribute("billings", billings);
		
		// Displaying summary in the billing page
		int totalNumOfBillings = billingRepository.countTotalNumOfBillings();
        int numPaid = billingRepository.countNumPaid();
        int numUnpaid = billingRepository.countNumUnpaid();
        int numOverdue = billingRepository.countNumOverdue();
        model.addAttribute("totalNumOfBillings", totalNumOfBillings);
        model.addAttribute("numPaid", numPaid);
        model.addAttribute("numUnpaid", numUnpaid);
        model.addAttribute("numOverdue", numOverdue);
		return "billing";
	}
	
	/* Method to edit billing, opening edit-billing.html */
	@GetMapping("/billing/edit/{billingId}")
	public String showEditBillingPage(@PathVariable("billingId") int billingId, Model model) {
		Billing billing = billingService.findBillingById(billingId);
		model.addAttribute("billing", billing);
	    
		return "edit-billing";
	}

	/* This method is responsible for updating an existing billing record in the system.
	 * It retrieves existing billing details based on the input in edit-billing.html, 
	 * updates the payment status, and redirects the user to the billing page. */
	@PostMapping("/billing/edit")
	public String updateBilling(@ModelAttribute("billing") Billing billing, 
			@RequestParam("paymentStatus") String paymentStatus) {
		// Retrieve existing billing from the database
		Billing existingBilling = billingService.findBillingById(billing.getBillingId());
		// Update the payment status and update the billing
		existingBilling.setPaymentStatus(paymentStatus);
		billingService.updateBilling(existingBilling);
	    
	    return "redirect:/billing?message=The+billing+has+been+updated+successfully!" + 
	    		"+Billing+ID%3A+" + existingBilling.getBillingId() + 
	    		",+Updated+Payment+Status%3A+" + existingBilling.getPaymentStatus();
	}
}
