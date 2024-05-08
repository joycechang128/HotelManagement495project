package com.cmsc495.hotelmanagementapp;

/*
* File: customerTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/customerTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Customer.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.billing.*;
import com.cmsc495.hotelmanagementapp.reservation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class CustomerTest {

    @InjectMocks
    private Customer customer;

    @Mock
    private CustomerService customerService;

    @Mock
    private Reservation reservation;

    @Mock
    private Billing billing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test setting and getting customer ID
    @Test
    void testGetAndSetCustomerId() {
        customer.setCustomerId(1);
        assertEquals(1, customer.getCustomerId(), "Customer ID should be set and retrieved correctly.");
    }

    // Test setting and getting customer name
    @Test
    void testGetAndSetCustomerName() {
        customer.setCustomerName("John Doe");
        assertEquals("John Doe", customer.getCustomerName(), "Customer name should be set and retrieved correctly.");
    }

    // Test setting and getting customer email
    @Test
    void testGetAndSetCustomerEmail() {
        customer.setCustomerEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", customer.getCustomerEmail(), "Customer email should be set and retrieved correctly.");
    }

    // Test setting and getting customer phone number
    @Test
    void testGetAndSetCustomerPhoneNumber() {
        customer.setCustomerPhoneNumber("123-456-7890");
        assertEquals("123-456-7890", customer.getCustomerPhoneNumber(), "Customer phone number should be set and retrieved correctly.");
    }

    /*// Test getting reservations
    @Test
    void testGetReservations() {
        List<Reservation> reservations = Arrays.asList(reservation);
        customer.setReservations(reservations);
        assertEquals(reservations, customer.getReservations(), "Reservations should be retrieved correctly.");
    }

    // Test getting billings
    @Test
    void testGetBillings() {
        List<Billing> billings = Arrays.asList(billing);
        customer.setBillings(billings);
        assertEquals(billings, customer.getBillings(), "Billings should be retrieved correctly.");
    }*/

    // Test getting overall payment status through a service method
    @Test
    void testGetOverallPaymentStatus() {
        when(customerService.getOverallPaymentStatus(customer)).thenReturn("Paid");
        String overallPaymentStatus = customer.getOverallPaymentStatus();
        assertEquals("Paid", overallPaymentStatus, "Overall payment status should be computed and retrieved correctly.");
        verify(customerService).getOverallPaymentStatus(customer);
    }
}