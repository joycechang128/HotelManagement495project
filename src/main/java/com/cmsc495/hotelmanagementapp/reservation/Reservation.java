package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: Reservation.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/Reservation.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// reservationId value is generated automatically by the database
	@Column(name = "ReservationID")
	private int reservationId;
	
	@ManyToOne
	@JoinColumn(name="CustomerID", referencedColumnName="CustomerID")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name="RoomID", referencedColumnName="RoomID")
	private Room room;
	
	@OneToOne
	@JoinColumn(name="BillingID", referencedColumnName="BillingID")
	private Billing billing;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CheckInDate", nullable = false)
    private Date checkInDateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CheckOutDate", nullable = false)
    private Date checkOutDateTime;

	public Reservation() {}
	
	/* constructor, reservationId is not required because it is generated automatically by the database */
	public Reservation(Customer customer, Room room, Billing billing,
			Date checkInDateTime, Date checkOutDateTime) {
		super();
		this.customer = customer;
		this.room = room;
		this.billing = billing;
		this.checkInDateTime = checkInDateTime;
		this.checkOutDateTime = checkOutDateTime;
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
		return checkInDateTime;
	}
	
	public void setCheckInDate(Date checkInDateTime) {
		this.checkInDateTime = checkInDateTime;
	}
	
	public Date getCheckOutDate() {
		return checkOutDateTime;
	}
	
	public void setCheckOutDate(Date checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}
	
	public Billing getBilling() {
		return billing;
	}
}
