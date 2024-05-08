package com.cmsc495.hotelmanagementapp;

/*
* File: cusotmerRepositoryTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/customerRepositoryTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Cusotmer Repository.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.cmsc495.hotelmanagementapp.customer.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    // Test creating and retrieving a customer
    @Test
    void testSaveAndFind() {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("Jane Doe");
        newCustomer.setCustomerEmail("jane.doe@aol.com");
        newCustomer.setCustomerPhoneNumber("123-456-7890");
        newCustomer = entityManager.persistFlushFind(newCustomer);

        Customer foundCustomer = customerRepository.findById(newCustomer.getCustomerId()).orElse(null);
        assertNotNull(foundCustomer, "Customer should be found after being saved.");
        assertEquals("John Doe", foundCustomer.getCustomerName(), "Customer name should match the saved value.");
    }

    // Test finding a customer by name
    @Test
    void testFindByCustomerName() {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("Alice Smith");
        newCustomer.setCustomerEmail("alice.smith@example.com");
        newCustomer.setCustomerPhoneNumber("987-654-3210");
        entityManager.persistAndFlush(newCustomer);

        Customer foundCustomer = customerRepository.findByCustomerName("Alice Smith");
        assertNotNull(foundCustomer, "Customer should be found by name.");
        assertEquals("Alice Smith", foundCustomer.getCustomerName(), "Customer name should match the query.");
    }

    // Test deleting a customer by ID
    @Test
    void testDeleteCustomerByCustomerId() {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("Bob Johnson");
        newCustomer.setCustomerEmail("bob.johnson@example.com");
        newCustomer.setCustomerPhoneNumber("456-789-0123");
        newCustomer = entityManager.persistFlushFind(newCustomer);

        customerRepository.deleteCustomerByCustomerId(newCustomer.getCustomerId());
        Customer deletedCustomer = customerRepository.findById(newCustomer.getCustomerId()).orElse(null);
        assertNull(deletedCustomer, "Customer should be deleted and not found.");
    }

    // Test counting all customers
    @Test
    void testCountAllCustomers() {
        Customer customer1 = new Customer("Emma Brown", "emma.brown@example.com", "654-321-0987", null, null);
        Customer customer2 = new Customer("David Wilson", "david.wilson@example.com", "321-654-0987", null, null);
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();

        int count = customerRepository.countAllCustomers();
        assertEquals(2, count, "Should count all customers correctly.");
    }

    // Test retrieving all customers
    @Test
    void testFindAll() {
        Customer customer1 = new Customer("Emma Brown", "emma.brown@example.com", "654-321-0987", null, null);
        Customer customer2 = new Customer("David Wilson", "david.wilson@example.com", "321-654-0987", null, null);
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();

        List<Customer> customers = customerRepository.findAll();
        assertEquals(2, customers.size(), "Should retrieve all customers correctly.");
    }
}