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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "room")
public class Room {

	@Id													// primary key of the database table
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// reservationId value is generated automatically by the database
	@Column(name = "RoomID")
	private int roomId;
	
	@Column(name = "RoomNumber", nullable = false)
	private int roomNumber;
	
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
