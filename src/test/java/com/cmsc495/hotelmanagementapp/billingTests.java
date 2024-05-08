package com.cmsc495.hotelmanagementapp;

/*
* File: billingTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/billingTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Billing.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;

import com.cmsc495.hotelmanagementapp.billing.*;
import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.reservation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BillingTest {

    @InjectMocks
    private Billing billing; //Injects the Billing instance with mocks

    @Mock
    private Customer customer; //Mocks Customer for isolation

    @Mock
    private Reservation reservation; //Mocks Reservation to test without database interaction

    @Mock
    private BillingService billingService; //Mocks BillingService for testing getAmount()

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Initializes mocks before each test
    }

    @Test
    void testGetAndSetBillingId() {
        //Test for setting and getting billing ID
        billing.setBillingId(123);
        assertEquals(123, billing.getBillingId(), "Billing ID should be set and retrieved correctly.");
    }

    @Test
    void testGetAndSetCustomer() {
        //Test for setting and getting customer
        billing.setCustomer(customer);
        assertEquals(customer, billing.getCustomer(), "Customer should be set and retrieved correctly.");
    }

    @Test
    void testGetAndSetReservation() {
        //Test for setting and getting reservation
        billing.setReservation(reservation);
        assertEquals(reservation, billing.getReservation(), "Reservation should be set and retrieved correctly.");
    }

    @Test
    void testGetAndSetPaymentStatus() {
        //Test for setting and getting payment status
        billing.setPaymentStatus("Paid");
        assertEquals("Paid", billing.getPaymentStatus(), "Payment status should be set and retrieved correctly.");
    }

    @Test
    void testGetAmount() {
        //Test for calculating and retrieving the billing amount
        when(reservation.getCheckInDate()).thenReturn(new Date());
        when(reservation.getCheckOutDate()).thenReturn(new Date());
        when(billingService.getCalculatedAmount(reservation)).thenReturn(200.0);
        billing.setReservation(reservation);
        assertEquals(200.0, billing.getAmount(), 0.01, "Amount should be calculated and retrieved correctly.");
    }

    @Test
    void testGetCustomerName() {
        //Test for retrieving customer's name through Billing class
        when(customer.getCustomerName()).thenReturn("John Doe");
        billing.setCustomer(customer);
        assertEquals("John Doe", billing.getCustomerName(), "Customer name should be retrieved correctly.");
    }

    @Test
    void testGetCheckInDate() {
        //Test for retrieving check-in date from reservation
        Date checkInDate = new Date();
        when(reservation.getCheckInDate()).thenReturn(checkInDate);
        billing.setReservation(reservation);
        assertEquals(checkInDate, billing.getCheckInDate(), "Check-in date should be retrieved correctly.");
    }

    @Test
    void testGetCheckOutDate() {
        //Test for retrieving check-out date from reservation
        Date checkOutDate = new Date();
        when(reservation.getCheckOutDate()).thenReturn(checkOutDate);
        billing.setReservation(reservation);
        assertEquals(checkOutDate, billing.getCheckOutDate(), "Check-out date should be retrieved correctly.");
    }
}