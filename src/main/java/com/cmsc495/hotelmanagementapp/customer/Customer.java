package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: Customer.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/Customer.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	
	@Id													// primary key of the database table
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// reservationId value is generated automatically by the database
	@Column(name = "CustomerID")
	private int customerId;
	
	@Column(name = "CustomerName", nullable = false)
	private String customerName;
	
	// constructor
	public Customer() {}

	public Customer(String customerName) {
		super();
		this.customerName = customerName;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
}
