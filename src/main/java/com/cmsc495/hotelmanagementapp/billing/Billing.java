package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: Billing.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/Billing.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-04 
 * Description: This file contains the entity class that represents the a hotel customer's billing.
 *              It contains information such as billing id, payment status, 
 *              and associated customer and reservation details.
 */

import java.util.Date;

import org.hibernate.annotations.Formula;

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
import jakarta.persistence.Transient;

@Entity
@Table(name = "billing")
public class Billing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BillingID")
	private int billingId;
	
	@ManyToOne
	@JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
	private Customer customer;
	
	@OneToOne(mappedBy = "billing")
	private Reservation reservation;
	
	@Transient
	private double amount;
	
	@Column(name = "PaymentStatus", nullable = false)
	private String paymentStatus;
	
	@Formula("(SELECT COUNT(*) FROM billing)")
    private int totalNumOfBillings;

    @Formula("(SELECT COUNT(*) FROM billing WHERE paymentStatus = 'Paid')")
    private int numPaid;

    @Formula("(SELECT COUNT(*) FROM billing WHERE paymentStatus = 'Unpaid')")
    private int numUnpaid;

    @Formula("(SELECT COUNT(*) FROM billing WHERE paymentStatus = 'Overdue')")
    private int numOverdue;
	
	// constructor
	public Billing() {}

	public Billing(Customer customer, Reservation reservation, String paymentStatus) {
		super();
		this.customer = customer;
		this.reservation = reservation;
		this.paymentStatus = paymentStatus;
	}

	public int getBillingId() {
		return billingId;
	}
	
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getCustomerName() {
		return customer.getCustomerName();
	}
	
	public Reservation getReservation() {
		return reservation;
	}
	
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	public Date getCheckInDate() {
		return reservation.getCheckInDate();
	}
	
	public Date getCheckOutDate() {
		return reservation.getCheckOutDate();
	}
	
	public double getAmount() {
		BillingService billingService = new BillingService();
		amount = billingService.getCalculatedAmount(this.reservation);
		return amount;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
