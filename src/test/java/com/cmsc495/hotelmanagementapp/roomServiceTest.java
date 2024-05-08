package com.cmsc495.hotelmanagementapp;

/*
* File: roomServiceTest.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/roomServiceTest.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Room Service.
*/

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomRepository;
import com.cmsc495.hotelmanagementapp.room.RoomService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import com.cmsc495.hotelmanagementapp.reservation.Reservation;
import com.cmsc495.hotelmanagementapp.reservation.ReservationService;

class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
    }

    // Test retrieving all rooms
    @Test
    void testGetAllRooms() {
        List<Room> expectedRooms = Arrays.asList(new Room(101, 1, "Deluxe", true, "Clean"), new Room(102, 1, "Standard", true, "Dirty"));
        when(roomRepository.findAll()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.getAllRooms();

        assertEquals(2, actualRooms.size(), "Should return all rooms.");
        assertTrue(actualRooms.stream().allMatch(r -> r instanceof Room), "All items should be Room instances.");
    }

    // Test counting available rooms
    @Test
    void testCountAvailableRooms() {
        List<Room> rooms = Arrays.asList(
            new Room(101, 1, "Deluxe", true, "Clean"),
            new Room(102, 1, "Standard", false, "Clean")
        );
        when(roomRepository.findAll()).thenReturn(rooms);

        int count = roomService.countAvailableRooms();

        assertEquals(1, count, "Should count only available rooms.");
    }

    // Test counting rooms prepared for cleaning
    @Test
    void testCountCleaningPreparedRooms() {
        List<Room> rooms = Arrays.asList(
            new Room(101, 1, "Deluxe", true, "Prepared"),
            new Room(102, 1, "Standard", true, "Dirty")
        );
        when(roomRepository.findAll()).thenReturn(rooms);

        int count = roomService.countCleaningPreparedRooms();

        assertEquals(1, count, "Should count only rooms prepared for cleaning.");
    }

    // Test creating a new room
    @Test
    void testCreateRoom() {
        Room room = new Room(103, 1, "Suite", true, "Clean");
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        roomService.createRoom(room);

        verify(roomRepository).save(room);
    }

    // Test updating a room
    @Test
    void testUpdateRoom() {
        Room existingRoom = new Room(104, 1, "Suite", true, "Dirty");
        Room updatedRoom = new Room(104, 1, "Suite", true, "Clean");
        when(roomRepository.findById(104)).thenReturn(existingRoom);
        when(roomRepository.save(any(Room.class))).thenReturn(updatedRoom);

        Room result = roomService.updateRoom(104, updatedRoom);

        assertEquals("Clean", result.getCleaningStatus(), "Room should be updated with new cleaning status.");
    }

    // Test deleting a room with no reservations
    @Test
    void testDeleteRoom() {
        int roomId = 102;
        when(reservationService.getReservationsByRoomId(roomId)).thenReturn(Arrays.asList());

        assertDoesNotThrow(() -> roomService.deleteRoom(roomId), "Deleting a room without reservations should not throw an exception.");
        verify(roomRepository).deleteById(roomId);
    }

    // Test deleting a room with existing reservations
    @Test
    void testDeleteRoomWithReservations() {
        int roomId = 101;
        List<Reservation> reservations = Arrays.asList(new Reservation());
        when(reservationService.getReservationsByRoomId(roomId)).thenReturn(reservations);

        assertThrows(ResponseStatusException.class, () -> roomService.deleteRoom(roomId), "Deleting a room with reservations should throw an exception.");
    }

    // Test retrieving a room by ID
    @Test
    void testGetRoomById() {
        int roomId = 202;
        Room expectedRoom = new Room(202, 2, "Suite", true, "Clean");
        when(roomRepository.findById(roomId)).thenReturn(expectedRoom);

        Room actualRoom = roomService.getRoomById(roomId);

        assertEquals(expectedRoom, actualRoom, "The retrieved room should match the expected room.");
    }

    // Test retrieving a room by room number
    @Test
    void testGetRoomByNumber() {
        int roomNumber = 203;
        Room expectedRoom = new Room(203, 2, "Suite", true, "Clean");
        when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(expectedRoom);

        Room actualRoom = roomService.getRoomByNumber(roomNumber);

        assertEquals(expectedRoom, actualRoom, "The retrieved room should match the expected room.");
    }
}