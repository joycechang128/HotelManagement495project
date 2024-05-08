package com.cmsc495.hotelmanagementapp;

/*
* File: reservationServiceTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/reservationServiceTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Reservation Service.
*/

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cmsc495.hotelmanagementapp.reservation.*;
import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.room.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test retrieving all reservations
    @Test
    void testGetAllReservations() {
        List<Reservation> expectedReservations = Arrays.asList(new Reservation());
        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.getAllReservations();

        assertEquals(expectedReservations, actualReservations, "Should return all reservations.");
    }

    // Test finding available dates for a room (both check-in and check-out scenarios)
    @Test
    void testFindAvailableDatesForRoom() {
        Room room = new Room();
        room.setRoomId(1);
        when(roomRepository.findById(1)).thenReturn(room);
        when(reservationRepository.findByRoom(room)).thenReturn(Arrays.asList()); // No existing reservations

        List<LocalDate> dates = reservationService.findAvailableDatesForRoom(1, true, null);
        assertFalse(dates.isEmpty(), "Should return some available dates when there are no conflicts.");
    }

    // Test creating a reservation
    @Test
    void testCreateReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        reservationService.createReservation(reservation);

        verify(reservationRepository).save(reservation);
    }

    // Test updating a reservation
    @Test
    void testUpdateReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(reservation);

        assertEquals(reservation, updatedReservation, "The updated reservation should be returned.");
    }

    // Test finding a reservation by ID
    @Test
    void testFindReservationById() {
        int reservationId = 1;
        Optional<Reservation> expectedReservation = Optional.of(new Reservation());
        when(reservationRepository.findReservationByReservationId(reservationId)).thenReturn(expectedReservation);

        Optional<Reservation> actualReservation = reservationService.findReservationById(reservationId);

        assertEquals(expectedReservation, actualReservation, "Should return the reservation by ID.");
    }

    // Test deleting a reservation by ID
    @Test
    void testDeleteReservationById() {
        int reservationId = 1;
        doNothing().when(reservationRepository).deleteReservationByReservationId(reservationId);

        reservationService.deleteReservationById(reservationId);

        verify(reservationRepository).deleteReservationByReservationId(reservationId);
    }

    // Comprehensive test for determining available dates logic with a few edge cases
    @Test
    void testComplexDateCalculations() {
        Room room = new Room();
        room.setRoomId(1);
        LocalDate now = LocalDate.now();
        List<Reservation> reservations = Arrays.asList(
            new Reservation(null, room, null, java.sql.Date.valueOf(now.plusDays(1)), java.sql.Date.valueOf(now.plusDays(3))),
            new Reservation(null, room, null, java.sql.Date.valueOf(now.plusDays(5)), java.sql.Date.valueOf(now.plusDays(7)))
        );
        when(roomRepository.findById(1)).thenReturn(room);
        when(reservationRepository.findByRoom(room)).thenReturn(reservations);

        // Test a day before any reservation starts
        assertTrue(reservationService.findAvailableDatesForRoom(1, true, null).contains(now),
            "Dates before the first reservation should be available.");

        // Test a day during an ongoing reservation
        assertFalse(reservationService.findAvailableDatesForRoom(1, true, null).contains(now.plusDays(2)),
            "Dates during an existing reservation should not be available.");

        // Test a day between two reservations
        assertTrue(reservationService.findAvailableDatesForRoom(1, true, null).contains(now.plusDays(4)),
            "Dates between reservations should be available.");

        // Test a day after the last reservation ends
        assertTrue(reservationService.findAvailableDatesForRoom(1, true, null).contains(now.plusDays(8)),
            "Dates after the last reservation should be available.");
    }
}