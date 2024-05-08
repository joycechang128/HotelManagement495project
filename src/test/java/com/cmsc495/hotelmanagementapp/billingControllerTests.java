package com.cmsc495.hotelmanagementapp;

/*
* File: billingControllerTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/billingControllerTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Billing Controller.
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.cmsc495.hotelmanagementapp.billing.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BillingController.class)
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @MockBean
    private BillingRepository billingRepository;

    // Test the retrieval and display of all billings
    @Test
    void testGetAllBillings() throws Exception {
        List<Billing> billings = Arrays.asList(new Billing(), new Billing());
        when(billingService.getAllBillings()).thenReturn(billings);
        when(billingRepository.countTotalNumOfBillings()).thenReturn(2);
        when(billingRepository.countNumPaid()).thenReturn(1);
        when(billingRepository.countNumUnpaid()).thenReturn(1);
        when(billingRepository.countNumOverdue()).thenReturn(0);

        mockMvc.perform(get("/billing"))
                .andExpect(status().isOk())
                .andExpect(view().name("billing"))
                .andExpect(model().attributeExists("billings"))
                .andExpect(model().attribute("billings", billings))
                .andExpect(model().attribute("totalNumOfBillings", 2))
                .andExpect(model().attribute("numPaid", 1))
                .andExpect(model().attribute("numUnpaid", 1))
                .andExpect(model().attribute("numOverdue", 0));
    }

    // Test the display of the edit billing page
    @Test
    void testShowEditBillingPage() throws Exception {
        Billing billing = new Billing();
        int billingId = 1;
        when(billingService.findBillingById(billingId)).thenReturn(billing);

        mockMvc.perform(get("/billing/edit/{billingId}", billingId))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-billing"))
                .andExpect(model().attributeExists("billing"))
                .andExpect(model().attribute("billing", billing));
    }

    // Test updating a billing record
    @Test
    void testUpdateBilling() throws Exception {
        Billing billing = new Billing();
        billing.setBillingId(1);
        billing.setPaymentStatus("Unpaid");

        when(billingService.findBillingById(1)).thenReturn(billing);

        mockMvc.perform(post("/billing/edit")
                .param("billingId", "1")
                .param("paymentStatus", "Paid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/billing?message=The+billing+has+been+updated+successfully!+Billing+ID%3A+1,+Updated+Payment+Status%3A+Paid"));

        verify(billingService).updateBilling(billing);
    }
}