package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: Reservation.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/Reservation.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-19
 * Description: This file contains...
 * 				...
 */

import java.util.Date;
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
	
	@Id													// primary key of the database table
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
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CheckInDate", nullable = false)
    private Date checkInDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CheckOutDate", nullable = false)
    private Date checkOutDate;

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
	
	public int getCustomerId() {
		return customer.getCustomerId();
	}
	
	public String getCustomerName() {
		return customer.getCustomerName();
	}
	
	public int getRoomId() {
		return room.getRoomId();
	}
	
	public int getRoomNumber() {
		return room.getRoomNumber();
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
	
	public Billing getBilling() {
		return billing;
	}
}
