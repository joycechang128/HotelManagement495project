package com.cmsc495.hotelmanagementapp;

/*
* File: reservationRepositoryTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/reservationRepositoryTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Reservation Repository.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.room.*;
import com.cmsc495.hotelmanagementapp.reservation.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    // Test saving and retrieving a reservation
    @Test
    void testSaveAndFind() {
        // Create and persist a mock reservation
        Customer customer = new Customer();
        Room room = new Room();
        entityManager.persist(customer);
        entityManager.persist(room);
        Reservation reservation = new Reservation(customer, room, null, new java.util.Date(), new java.util.Date());
        reservation = entityManager.persistFlushFind(reservation);

        // Retrieving the reservation by ID using the repository
        Optional<Reservation> found = reservationRepository.findReservationByReservationId(reservation.getReservationId());
        assertTrue(found.isPresent(), "Reservation should be found.");
        assertEquals(reservation.getReservationId(), found.get().getReservationId(), "Reservation IDs should match.");
    }

    // Test retrieving all reservations
    @Test
    void testFindAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        assertNotNull(reservations, "Should return a list, even if empty.");
    }

    // Test finding reservations by room
    @Test
    void testFindByRoom() {
        Room room = new Room(); // Assume room has an ID set
        entityManager.persist(room);
        Reservation reservation = new Reservation(null, room, null, new java.util.Date(), new java.util.Date());
        entityManager.persist(reservation);
        entityManager.flush();

        List<Reservation> results = reservationRepository.findByRoom(room);
        assertEquals(1, results.size(), "Should find one reservation for the specified room.");
    }

    // Test finding reservations by customer
    @Test
    void testFindByCustomer() {
        Customer customer = new Customer(); // Assume customer has an ID set
        entityManager.persist(customer);
        Reservation reservation = new Reservation(customer, null, null, new java.util.Date(), new java.util.Date());
        entityManager.persist(reservation);
        entityManager.flush();

        List<Reservation> results = reservationRepository.findByCustomer(customer);
        assertEquals(1, results.size(), "Should find one reservation for the specified customer.");
    }

    // Test deleting a reservation by ID
    @Test
    void testDeleteReservationByReservationId() {
        Reservation reservation = new Reservation();
        reservation = entityManager.persistFlushFind(reservation);
        int id = reservation.getReservationId();

        reservationRepository.deleteReservationByReservationId(id);
        Optional<Reservation> deleted = reservationRepository.findById(id);
        assertTrue(deleted.isEmpty(), "Reservation should be deleted.");
    }

    // Test counting all reservations
    @Test
    void testCountAllReservations() {
        int initialCount = reservationRepository.countAllReservations();

        // Since the setup has preloaded data, this test checks if count increases correctly after adding one
        Customer customer = new Customer();
        Room room = new Room();
        entityManager.persist(customer);
        entityManager.persist(room);
        Reservation reservation = new Reservation(customer, room, null, new java.util.Date(), new java.util.Date());
        entityManager.persist(reservation);
        entityManager.flush();

        int newCount = reservationRepository.countAllReservations();
        assertEquals(initialCount + 1, newCount, "Total count of reservations should increase by one.");
    }
}