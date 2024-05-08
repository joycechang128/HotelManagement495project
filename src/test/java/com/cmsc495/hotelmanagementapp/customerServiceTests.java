package com.cmsc495.hotelmanagementapp;

/*
* File: cusotmerServiceTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/cusotmerServiceTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Customer Service.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//import com.cmsc495.hotelmanagementapp.billing.*;
import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.reservation.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;


class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ReservationService reservationService;

    // Test retrieving all customers
    @Test
    void testGetAllCustomers() {
        List<Customer> expectedCustomers = Arrays.asList(new Customer());
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> actualCustomers = customerService.getAllCustomers();

        assertEquals(expectedCustomers, actualCustomers, "Should return all customers from repository.");
    }

    /*// Test retrieving all customers sorted by names
    @Test
    void testGetAllCustomersSortedByNames() {
        List<Customer> expectedCustomers = Arrays.asList(new Customer());
        when(customerRepository.findAll(any())).thenReturn(expectedCustomers);

        List<Customer> actualCustomers = customerService.getAllCustomersSortedByNames();

        assertEquals(expectedCustomers, actualCustomers, "Should return customers sorted by names.");
    }*/

    // Test creating a customer
    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        doNothing().when(customerRepository).save(customer);

        customerService.createCustomer(customer);

        verify(customerRepository).save(customer);
    }

    // Test updating a customer
    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(customer);

        assertEquals(customer, updatedCustomer, "Should return the updated customer.");
    }

    // Test deleting a customer with no reservations
    @Test
    void testDeleteCustomerWithNoReservations() {
        int customerId = 1;
        when(reservationService.getReservationsByCustomerId(customerId)).thenReturn(Arrays.asList());

        doNothing().when(customerRepository).deleteCustomerByCustomerId(customerId);

        customerService.deleteCustomer(customerId);

        verify(customerRepository).deleteCustomerByCustomerId(customerId);
    }

    // Test deleting a customer with reservations should fail
    @Test
    void testDeleteCustomerWithReservations() {
        int customerId = 1;
        when(reservationService.getReservationsByCustomerId(customerId)).thenReturn(Arrays.asList(new Reservation()));

        assertThrows(ResponseStatusException.class, () -> customerService.deleteCustomer(customerId),
            "Should throw ResponseStatusException when trying to delete a customer with reservations.");
    }

    // Test deleting a customer that does not exist
    @Test
    void testDeleteCustomerNotFound() {
        int customerId = 1;
        when(reservationService.getReservationsByCustomerId(customerId)).thenReturn(Arrays.asList());
        doThrow(new EmptyResultDataAccessException(1)).when(customerRepository).deleteCustomerByCustomerId(customerId);

        assertThrows(ResponseStatusException.class, () -> customerService.deleteCustomer(customerId),
            "Should throw ResponseStatusException if customer does not exist.");
    }

    // Test getting overall payment status
    /*@Test
    void testGetOverallPaymentStatus() {
        Customer customer = new Customer();
        Billing paidBilling = new Billing();
        paidBilling.setPaymentStatus("Paid");
        Billing unpaidBilling = new Billing();
        unpaidBilling.setPaymentStatus("Unpaid");
        customer.setBillings(Arrays.asList(paidBilling, unpaidBilling));

        String status = customerService.getOverallPaymentStatus(customer);

        assertEquals("Unpaid", status, "Should return 'Unpaid' if there are unpaid billings.");
    }*/
}