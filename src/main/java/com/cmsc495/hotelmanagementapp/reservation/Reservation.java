package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: Reservation.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/Reservation.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-30
 * Description: This file contains the entity class that represents the a hotel customer's reservation.
 *              It contains information such as check-in & check-out date, 
 *              and associated customer, room, and billing details.
 */

import java.util.Date;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.cmsc495.hotelmanagementapp.billing.Billing;
import com.cmsc495.hotelmanagementapp.customer.Customer;
import com.cmsc495.hotelmanagementapp.room.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "reservation")
public class Reservation {
	
	@Id	// primary key of the database table
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// reservationId value is generated automatically by the database
	@Column(name = "ReservationID")
	private int reservationId;
	
	@ManyToOne
	@JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "RoomID", referencedColumnName = "RoomID")
	private Room room;
	
	@OneToOne
	@JoinColumn(name = "BillingID", referencedColumnName = "BillingID")
	private Billing billing;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "CheckInDate", nullable = false)
	private Date checkInDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "CheckOutDate", nullable = false)
	private Date checkOutDate;
	
	// Summary attributes
	@Formula(value = "(SELECT COUNT(*) FROM reservation r)")
	private int totalNumOfReservations;
	
	@Formula(value = "(SELECT COUNT(*) FROM customer)")
	private int totalNumOfCustomers;

	/* constructor, reservationId is not required because it is generated automatically by the database */
	public Reservation() {
	}
	
	public Reservation(Customer customer, Room room, Billing billing,
			Date checkInDate, Date checkOutDate) {
		super();
		this.customer = customer;
		this.room = room;
		this.billing = billing;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
	
	public int getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getCustomerId() {
		return customer.getCustomerId();
	}
	
	public void setCustomerId(int customerId) {
		this.customer.setCustomerId(customerId);
	}
	
	public String getCustomerName() {
		return customer.getCustomerName();
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public int getRoomId() {
		return room.getRoomId();
	}
	
	public void setRoomId(int roomId) {
		this.room.setRoomId(roomId);
	}
	
	public int getRoomNumber() {
		return room.getRoomNumber();
	}
	
	public Billing getBilling() {
		return billing;
	}
	
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	
	public int getBillingId() {
		return billing.getBillingId();
	}
	
	public String getPaymentStatus() {
		return billing.getPaymentStatus();
	}
	
	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public int getTotalNumOfReservations() {
		return totalNumOfReservations;
	}
	
	public int getTotalNumOfCustomers() {
		return totalNumOfCustomers;
	}

	// the method for debugging
	@Override
	public String toString() {
	    return "Reservation ID: " + reservationId + ", Customer ID: " + customer.getCustomerId() + ", Room ID: " + room.getRoomId() + ", Check-In Date: " + checkInDate + ", Check-Out Date: " + checkOutDate + ", Billing ID: " + billing.getBillingId();
	}
}
