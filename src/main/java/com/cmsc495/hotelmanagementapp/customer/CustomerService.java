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
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
}
