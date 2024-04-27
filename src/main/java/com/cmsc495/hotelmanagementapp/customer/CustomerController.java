package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerController.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Chia-Yu(Joyce) Chang, Mrigank Gupta
 * Created: 2024-04-11
 * Last Modified: 2024-04-27 
 * Description: This controller manages the operations related to customers in the hotel customer system.
 *              It provides mappings for creating, updating, and deleting customers, 
 *              as well as retrieving information about all customers.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/* The value="customer" will map to the <a href="customer"> tag in html -> the customer management button 
	 * This method retrieves customers data from the database and stores in the model for rendering in the view */
	@GetMapping("/customer")
	public String getAllCustomers(Model model) {
		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "customer";
	}
}
