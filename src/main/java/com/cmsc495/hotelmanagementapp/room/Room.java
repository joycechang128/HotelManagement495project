package com.cmsc495.hotelmanagementapp.room;
/*
 * File: Room.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/Room.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-04-16
 * Description: This file contains...
 * 				...
 */

import java.sql.Timestamp;

public class Room {
    private int roomId;
    private int roomNumber;
    private int roomFloor;
    private String roomType;
    private boolean availability;
    private String cleaningStatus;
    private Timestamp lastCleaningDate;
    private int housekeepingId;

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

    public Timestamp getLastCleaningDate() {
        return lastCleaningDate;
    }

    public void setLastCleaningDate(Timestamp lastCleaningDate) {
        this.lastCleaningDate = lastCleaningDate;
    }

    public int getHousekeepingId() {
        return housekeepingId;
    }

    public void setHousekeepingId(int housekeepingId) {
        this.housekeepingId = housekeepingId;
    }
}