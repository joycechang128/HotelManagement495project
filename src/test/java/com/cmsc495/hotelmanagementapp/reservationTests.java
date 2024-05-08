package com.cmsc495.hotelmanagementapp;

/*
* File: reservationTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/reservationTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Reservation.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import com.cmsc495.hotelmanagementapp.reservation.*;
import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.room.*;
import com.cmsc495.hotelmanagementapp.billing.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReservationTest {

    @InjectMocks
    private Reservation reservation;

    @Mock
    private Customer customer;

    @Mock
    private Room room;

    @Mock
    private Billing billing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test setting and getting reservation ID
    @Test
    void testGetAndSetReservationId() {
        reservation.setReservationId(1);
        assertEquals(1, reservation.getReservationId(), "Reservation ID should be correctly set and retrieved.");
    }

    // Test setting and getting customer
    @Test
    void testGetAndSetCustomer() {
        reservation.setCustomer(customer);
        assertEquals(customer, reservation.getCustomer(), "Customer should be correctly set and retrieved.");
    }

    // Test setting and getting room
    @Test
    void testGetAndSetRoom() {
        reservation.setRoom(room);
        assertEquals(room, reservation.getRoom(), "Room should be correctly set and retrieved.");
    }

    // Test setting and getting billing
    @Test
    void testGetAndSetBilling() {
        reservation.setBilling(billing);
        assertEquals(billing, reservation.getBilling(), "Billing should be correctly set and retrieved.");
    }

    // Test setting and getting check-in and check-out dates
    @Test
    void testGetAndSetCheckInAndCheckOutDates() {
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        assertEquals(checkInDate, reservation.getCheckInDate(), "Check-in date should be correctly set and retrieved.");
        assertEquals(checkOutDate, reservation.getCheckOutDate(), "Check-out date should be correctly set and retrieved.");
    }
}