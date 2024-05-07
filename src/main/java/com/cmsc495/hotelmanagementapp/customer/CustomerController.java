package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerController.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta (Main Developer), Chia-Yu(Joyce) Chang, Bryce Campbell
 * Created: 2024-04-11
 * Last Modified: 2024-05-07 
 * Description: This controller manages the operations related to customers in the hotel customer system.
 *              It provides mappings for creating, updating, and deleting customers, 
 *              as well as retrieving information about all customers.
 */

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/customer/new")
	public String showCreateCustomerPage(Model model) {
	    model.addAttribute("customer", new Customer());
	    return "new-customer";
	}
	
	@PostMapping("/customer/save")
	public String saveCustomer(@ModelAttribute("customer") @Valid Customer customer, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	    if (bindingResult.hasErrors()) {
	        return "new-customer";
	    }
	    customerService.createCustomer(customer);
	    String message = String.format("Customer '%s' (ID: %d) has been created successfully.", 
	    		customer.getCustomerName(), customer.getCustomerId());
	    redirectAttributes.addFlashAttribute("successMessage", message);
	    
	    return "redirect:/customer";
	}
	
	@GetMapping("/customer/edit/{customerId}")
	public String showEditCustomerPage(@PathVariable("customerId") int customerId, Model model) {
	    Customer customer = customerService.findCustomerByCustomerId(customerId);
	    model.addAttribute("customer", customer);
	    return "edit-customer";
	}

	@PostMapping("/customer/edit")
	public String updateCustomer(@ModelAttribute("customer") @Valid Customer customer,
	                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	    if (bindingResult.hasErrors()) {
	        return "edit-customer";
	    }
	    customerService.updateCustomer(customer);
	    String message = String.format("Customer '%s' (ID: %d) has been updated successfully. New Email: %s, New Phone Number: %s", 
                customer.getCustomerName(), customer.getCustomerId(), customer.getCustomerEmail(), customer.getCustomerPhoneNumber());
	    redirectAttributes.addFlashAttribute("successMessage", message);
	    return "redirect:/customer";
	}

}
