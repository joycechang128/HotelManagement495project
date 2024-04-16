package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: Billing.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/Billing.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Bryce Campbell
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import com.cmsc495.hotelmanagementapp.customer.Customer;
import com.cmsc495.hotelmanagementapp.reservation.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "billing")
public class Billing {
	
	@Id													// primary key of the database table
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// reservationId value is generated automatically by the database
	@Column(name = "BillingID")
	private int billingId;
	
	@OneToOne
	@JoinColumn(name="ReservationID", referencedColumnName="ReservationID")
	private Reservation reservation;
	
	@ManyToOne
	@JoinColumn(name="CustomerID", referencedColumnName="CustomerID")
	private Customer customer;
	
	@Column(name = "PaymentStatus", nullable = false)
	private String paymentStatus;
	
	
	// constructor
	public Billing() {}

	public Billing(Reservation reservation, Customer customer, String paymentStatus) {
		super();
		this.reservation = reservation;
		this.customer = customer;
		this.paymentStatus = paymentStatus;
	}

	public int getBillingId() {
		return billingId;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}

}
