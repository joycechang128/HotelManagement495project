package com.cmsc495.hotelmanagementapp;

/*
* File: billingRepositoryTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/billingRepositoryTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Billing Repository.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.cmsc495.hotelmanagementapp.billing.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class BillingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillingRepository billingRepository;

    // Test saving a new billing record
    @Test
    void testSaveBilling() {
        Billing billing = new Billing(); // assuming default constructor and setters
        billing.setPaymentStatus("Unpaid");
        Billing savedBilling = billingRepository.save(billing);
        assertEquals(billing, entityManager.find(Billing.class, savedBilling.getBillingId()), "Billing should be saved and retrievable");
    }

    // Test finding a billing by ID
    @Test
    void testFindBillingByBillingId() {
        Billing billing = new Billing();
        billing.setPaymentStatus("Unpaid");
        entityManager.persist(billing);
        entityManager.flush();
        Optional<Billing> foundBilling = billingRepository.findBillingByBillingId(billing.getBillingId());
        assertTrue(foundBilling.isPresent(), "Billing should be found with the correct ID");
    }

    // Test deleting a billing by ID
    @Test
    void testDeleteBillingByBillingId() {
        Billing billing = new Billing();
        billing.setPaymentStatus("Unpaid");
        entityManager.persist(billing);
        entityManager.flush();
        billingRepository.deleteBillingByBillingId(billing.getBillingId());
        Optional<Billing> deletedBilling = billingRepository.findBillingByBillingId(billing.getBillingId());
        assertTrue(deletedBilling.isEmpty(), "Billing should be deleted");
    }

    // Test counting total number of billings
    @Test
    void testCountTotalNumOfBillings() {
        Billing billing1 = new Billing();
        billing1.setPaymentStatus("Paid");
        entityManager.persist(billing1);

        Billing billing2 = new Billing();
        billing2.setPaymentStatus("Unpaid");
        entityManager.persist(billing2);

        entityManager.flush();

        int count = billingRepository.countTotalNumOfBillings();
        assertEquals(2, count, "Should count total number of billings");
    }

    // Test counting billings by payment status
    @Test
    void testCountNumPaid() {
        Billing billing1 = new Billing();
        billing1.setPaymentStatus("Paid");
        entityManager.persist(billing1);

        Billing billing2 = new Billing();
        billing2.setPaymentStatus("Unpaid");
        entityManager.persist(billing2);

        entityManager.flush();

        int countPaid = billingRepository.countNumPaid();
        assertEquals(1, countPaid, "Should count number of 'Paid' billings");
    }
}