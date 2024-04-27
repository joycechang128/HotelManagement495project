package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerService.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta, Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-27
 * Description: This service class contains business logic operations related to customers in the hotel management system.  
 *              It provides methods for handling CRUD operations on customer data, as well as additional business logics.
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cmsc495.hotelmanagementapp.billing.Billing;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	/* This method is for displaying sorted customer list: by ascending first name */
	public List<Customer> getAllCustomersSortedByNames() {
		// Define sorting rules
		Sort.Order nameOrder = Sort.Order.asc("customerName");
		// Create a Sort object and specify the sorting rules
		Sort sortByName = Sort.by(nameOrder);
		// Call findAll method and pass the sorting rules
		return customerRepository.findAll(sortByName);
	}
	
	/* This method retrieves the overall payment status of a customer.
	 * If the customer has any unpaid or overdue payments, it will be indicated accordingly.
	 * If both overdue and unpaid payments are present, the overdue status will be displayed. */
	public String getOverallPaymentStatus(Customer customer) {
		List<Billing> billings = customer.getBillings();
		boolean allPaid = true;
		boolean hasUnpaid = false;
		boolean hasOverdue = false;
		
		// Iterate billings data of a customer
		for (Billing billing : billings) {
			if ("Overdue".equals(billing.getPaymentStatus())) {
				allPaid = false;
				hasOverdue = true;
			} else if ("Unpaid".equals(billing.getPaymentStatus())) {
				allPaid = false;
				hasUnpaid = true;
			}
		}
		
		if (allPaid) {
			return "Paid";
		} else if (hasOverdue) {
			return "Overdue";
		} else {
			return "Unpaid";
		}
	}
}
