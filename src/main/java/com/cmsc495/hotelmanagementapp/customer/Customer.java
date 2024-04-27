package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: Customer.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/Customer.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta, Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-27 
 * Description: This file contains the entity class that represents the a hotel customer.
 *              It contains information such as customer id, name, email, phone number, 
 *              and associated reservation and billing details.
 */

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Formula;

import com.cmsc495.hotelmanagementapp.billing.Billing;
import com.cmsc495.hotelmanagementapp.reservation.Reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CustomerID")
	private int customerId;
	
	@Column(name = "CustomerName", nullable = false)
	private String customerName;
	
	@Column(name = "Email", nullable = false)
	private String customerEmail;
	
	@Column(name = "PhoneNumber", nullable = false)
	private String customerPhoneNumber;
	
	@OneToMany(mappedBy = "customer")
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "customer")
	private List<Billing> billings;
	
	@Formula(value = "(SELECT COUNT(*) FROM reservation r WHERE r.CustomerID = customerId)")
	private int numOfReservations;
	
	@Transient
	private String overallPaymentStatus;
		
	// constructor
	public Customer() {}
	
	public Customer(String customerName, String customerEmail, String customerPhoneNumber, List<Reservation> reservations,
			List<Billing> billings) {
		super();
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhoneNumber = customerPhoneNumber;
		this.reservations = reservations;
		this.billings = billings;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public int getNumOfReservations() {
        return numOfReservations;
	}
	
	public List<Billing> getBillings() {
		return billings;
	}
	
	public String getOverallPaymentStatus() {
		CustomerService customerService = new CustomerService();
        overallPaymentStatus = customerService.getOverallPaymentStatus(this);
        return overallPaymentStatus;
    }
}
