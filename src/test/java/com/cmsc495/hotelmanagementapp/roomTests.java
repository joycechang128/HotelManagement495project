package com.cmsc495.hotelmanagementapp; 

/*
* File: roomTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/roomTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Room.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cmsc495.hotelmanagementapp.room.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTest {

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
    }

    // Test setting and getting the room ID
    @Test
    void testGetAndSetRoomId() {
        room.setRoomId(101);
        assertEquals(101, room.getRoomId(), "Room ID should be set and retrieved correctly.");
    }

    // Test setting and getting the room number
    @Test
    void testGetAndSetRoomNumber() {
        room.setRoomNumber(102);
        assertEquals(102, room.getRoomNumber(), "Room number should be set and retrieved correctly.");
    }

    // Test setting and getting the room floor
    @Test
    void testGetAndSetRoomFloor() {
        room.setRoomFloor(1);
        assertEquals(1, room.getRoomFloor(), "Room floor should be set and retrieved correctly.");
    }

    // Test setting and getting the room type
    @Test
    void testGetAndSetRoomType() {
        room.setRoomType("Deluxe");
        assertEquals("Deluxe", room.getRoomType(), "Room type should be set and retrieved correctly.");
    }

    // Test setting and getting the availability
    @Test
    void testGetAndSetAvailability() {
        room.setAvailability(true);
        assertEquals(true, room.isAvailability(), "Availability should be set and retrieved correctly.");
    }

    // Test setting and getting the cleaning status
    @Test
    void testGetAndSetCleaningStatus() {
        room.setCleaningStatus("Clean");
        assertEquals("Clean", room.getCleaningStatus(), "Cleaning status should be set and retrieved correctly.");
    }
}