package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerService.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
