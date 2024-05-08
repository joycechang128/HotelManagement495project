package com.cmsc495.hotelmanagementapp;

/*
* File: billingServiceTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/billingServiceTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Billing Service.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cmsc495.hotelmanagementapp.billing.*;
import com.cmsc495.hotelmanagementapp.reservation.*;
import com.cmsc495.hotelmanagementapp.room.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BillingServiceTest {

    @InjectMocks
    private BillingService billingService;

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private Reservation reservation;

    @Mock
    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Tests to verify that all billings are correctly retrieved from the repository
    @Test
    void testGetAllBillings() {
        List<Billing> expectedBillings = Arrays.asList(new Billing(), new Billing());
        when(billingRepository.findAll()).thenReturn(expectedBillings);

        List<Billing> actualBillings = billingService.getAllBillings();

        assertEquals(expectedBillings, actualBillings, "Should return all billings from the repository.");
    }

    //Tests to ensure that a new billing record is saved correctly
    @Test
    void testCreateBilling() {
        Billing billing = new Billing();
        when(billingRepository.save(billing)).thenReturn(billing);

        billingService.createBilling(billing);

        verify(billingRepository, times(1)).save(billing);
    }

    //Tests to verify that an existing billing record is updated correctly
    @Test
    void testUpdateBilling() {
        Billing billing = new Billing();
        when(billingRepository.save(billing)).thenReturn(billing);

        Billing returnedBilling = billingService.updateBilling(billing);

        assertEquals(billing, returnedBilling, "The updated billing should be returned.");
        verify(billingRepository, times(1)).save(billing);
    }

    //Tests that the correct billing record is retrieved by its ID
    @Test
    void testFindBillingById() {
        Billing billing = new Billing();
        int billingId = 1;
        when(billingRepository.findById(billingId)).thenReturn(Optional.of(billing));

        Billing foundBilling = billingService.findBillingById(billingId);

        assertEquals(billing, foundBilling, "Should return the correct billing by id.");
    }

    //Tests behavior when a billing record is not found by its ID
    @Test
    void testFindBillingByIdNotFound() {
        int billingId = 999;
        when(billingRepository.findById(billingId)).thenReturn(Optional.empty());

        Billing foundBilling = billingService.findBillingById(billingId);

        assertNull(foundBilling, "Should return null if billing is not found.");
    }

    //Tests that a billing record is deleted correctly
    @Test
    void testDeleteBillingById() {
        int billingId = 1;

        billingService.deleteBillingById(billingId);

        verify(billingRepository, times(1)).deleteById(billingId);
    }

    //Testing calculated amounts based on room type and reservation duration
    @Test
    void testGetCalculatedAmountSingleRoom() {
        when(reservation.getRoom()).thenReturn(room);
        when(room.getRoomType()).thenReturn("Single");
        when(reservation.getCheckInDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 10)));
        when(reservation.getCheckOutDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 12)));

        double calculatedAmount = billingService.getCalculatedAmount(reservation);

        assertEquals(291.6, calculatedAmount, 0.01, "The calculated amount should match for single room type for 2 days.");
    }

    @Test
    void testGetCalculatedAmountDoubleRoom() {
        when(reservation.getRoom()).thenReturn(room);
        when(room.getRoomType()).thenReturn("Double");
        when(reservation.getCheckInDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 10)));
        when(reservation.getCheckOutDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 13)));

        double calculatedAmount = billingService.getCalculatedAmount(reservation);

        assertEquals(542.16, calculatedAmount, 0.01, "The calculated amount should match for double room type for 3 days.");
    }

    @Test
    void testGetCalculatedAmountTripleRoom() {
        when(reservation.getRoom()).thenReturn(room);
        when(room.getRoomType()).thenReturn("Triple");
        when(reservation.getCheckInDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 10)));
        when(reservation.getCheckOutDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 14)));

        double calculatedAmount = billingService.getCalculatedAmount(reservation);

        assertEquals(894.0, calculatedAmount, 0.01, "The calculated amount should match for triple room type for 4 days.");
    }

    @Test
    void testGetCalculatedAmountFamilyRoom() {
        when(reservation.getRoom()).thenReturn(room);
        when(room.getRoomType()).thenReturn("Family");
        when(reservation.getCheckInDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 10)));
        when(reservation.getCheckOutDate()).thenReturn(Date.valueOf(LocalDate.of(2024, 4, 15)));

        double calculatedAmount = billingService.getCalculatedAmount(reservation);

        assertEquals(1556.0, calculatedAmount, 0.01, "The calculated amount should match for family room type for 5 days.");
    }
}