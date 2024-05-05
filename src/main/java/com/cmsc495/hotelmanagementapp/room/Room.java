package com.cmsc495.hotelmanagementapp.room;
/*
 * File: Room.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/Room.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-05-03
 * Description: This file defines the Room entity class. 
 *              It represents a room in a hotel with attributes 
 *              such as room number, floor, type, availability, 
 *              and cleaning status.
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

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RoomID")
    private int roomId;
    
    @Column(name = "RoomNumber", nullable = false)
    private int roomNumber;
    
    @Column(name = "RoomFloor", nullable = false)
    private int roomFloor;
    
    @Column(name = "RoomType", nullable = false)
    private String roomType;
    
    @Column(name = "Availability", nullable = false)
    private boolean availability;
    
    @Column(name = "CleaningStatus", nullable = false)
    private String cleaningStatus;
   
    public Room() {}

    public Room(int roomNumber, int roomFloor, String roomType, boolean availability, String cleaningStatus) {
        super();
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
        this.roomType = roomType;
        this.availability = availability;
        this.cleaningStatus = cleaningStatus;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(String cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }
}
