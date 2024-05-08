package com.cmsc495.hotelmanagementapp;

/*
* File: roomRepositoryTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/roomRepositoryTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Room Repository.
*/

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    // Test saving and retrieving a room
    @Test
    void testSaveAndFindById() {
        Room room = new Room(101, 1, "Deluxe", true, "Clean");
        room = entityManager.persistAndFlush(room);
        Room foundRoom = roomRepository.findById(room.getRoomId());

        assertNotNull(foundRoom, "Room should be found after being saved.");
        assertEquals(room.getRoomNumber(), foundRoom.getRoomNumber(), "Room number should match the saved room.");
    }

    // Test finding all rooms
    @Test
    void testFindAllRooms() {
        Room room1 = new Room(102, 2, "Standard", true, "Needs Cleaning");
        Room room2 = new Room(103, 2, "Suite", false, "Clean");
        entityManager.persist(room1);
        entityManager.persist(room2);
        entityManager.flush();

        List<Room> rooms = roomRepository.findAll();
        assertEquals(2, rooms.size(), "Should find all rooms persisted in the repository.");
    }

    // Test deleting a room
    @Test
    void testDeleteById() {
        Room room = new Room(104, 1, "Deluxe", true, "Clean");
        room = entityManager.persistAndFlush(room);
        int roomId = room.getRoomId();

        roomRepository.deleteById(roomId);
        Room deletedRoom = roomRepository.findById(roomId);
        assertNull(deletedRoom, "Room should be deleted and no longer retrievable.");
    }

    // Test finding a room by room number
    @Test
    void testFindByRoomNumber() {
        Room room = new Room(105, 1, "Suite", true, "Clean");
        entityManager.persistAndFlush(room);

        Room foundRoom = roomRepository.findByRoomNumber(105);
        assertNotNull(foundRoom, "Room should be found by room number.");
        assertEquals(105, foundRoom.getRoomNumber(), "Room number should match the query.");
    }
}