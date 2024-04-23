package com.cmsc495.hotelmanagementapp.room;
/*
 * File: Room.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/Room.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import java.util.Date;
import com.cmsc495.hotelmanagementapp.housekeeping.Housekeeping;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "room")
public class Room {

	@Id													// primary key of the database table
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// reservationId value is generated automatically by the database
	@Column(name = "RoomID")
	private int roomId;
	
	@Column(name = "RoomNumber", nullable = false)
	private int roomNumber;
	
	@Column(name = "RoomFloor")
    private int roomFloor;
    
    @Column(name = "RoomType")
    private String roomType;
    
    @Column(name = "Availability")
    private boolean availability;
    
    @Column(name = "CleaningStatus")
    private String cleaningStatus;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "LastCleaningDate")
    private Date lastCleaningDate;
    
    @ManyToOne
    @JoinColumn(name = "HousekeepingID", referencedColumnName= "HousekeepingID")
    private Housekeeping housekeeping;
	
	// constructor
	public Room() {}
	
	public Room(int roomNumber) {
		super();
		this.roomNumber = roomNumber;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}

}
