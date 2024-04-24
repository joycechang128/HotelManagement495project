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
    
    @Temporal(TemporalType.DATE)
    @Column(name = "LastCleaningDate")
    private Date lastCleaningDate;
    
    @ManyToOne
    @JoinColumn(name = "HousekeepingID", referencedColumnName= "HousekeepingID")
    private Housekeeping housekeeping;

    public Room() {}

    public Room(int roomNumber, int roomFloor, String roomType, boolean availability, String cleaningStatus, Date lastCleaningDate, Housekeeping housekeeping) {
        super();
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
        this.roomType = roomType;
        this.availability = availability;
        this.cleaningStatus = cleaningStatus;
        this.lastCleaningDate = lastCleaningDate;
        this.housekeeping = housekeeping;
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

    public Date getLastCleaningDate() {
        return lastCleaningDate;
    }

    public void setLastCleaningDate(Date lastCleaningDate) {
        this.lastCleaningDate = lastCleaningDate;
    }
    
    public int getHousekeepingId() {
		return housekeeping.getHousekeepingId();
	}
   
    public Housekeeping getHousekeeping() {
    	return housekeeping;
    }
}
