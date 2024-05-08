package com.cmsc495.hotelmanagementapp;

/*
* File: customerControllerTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/cusotmerControllerTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Customer Controller.
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Arrays;
import java.util.List;

import com.cmsc495.hotelmanagementapp.customer.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    // Test retrieving all customers
    @Test
    void getAllCustomersTest() throws Exception {
        List<Customer> customers = Arrays.asList(new Customer());
        given(customerService.getAllCustomers()).willReturn(customers);
        given(customerRepository.countAllCustomers()).willReturn(1);

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer"))
                .andExpect(model().attributeExists("customers", "totalNumOfCustomers"))
                .andExpect(model().attribute("totalNumOfCustomers", 1));
    }

    // Test displaying the form to create a new customer
    @Test
    void showCreateCustomerPageTest() throws Exception {
        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new-customer"))
                .andExpect(model().attributeExists("customer"));
    }

    // Test saving a new customer
    @Test
    void saveCustomerTest() throws Exception {
        mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("customerName", "Jane Doe")
                .param("customerEmail", "donut@krispykreme.com")
                .param("customerPhoneNumber", "678-999-8121"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer"));
    }

    // Test showing the edit customer page
    @Test
    void showEditCustomerPageTest() throws Exception {
        int customerId = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        given(customerService.findCustomerByCustomerId(customerId)).willReturn(customer);

        mockMvc.perform(get("/customer/edit/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-customer"))
                .andExpect(model().attributeExists("customer"));
    }

    // Test updating a customer
    @Test
    void updateCustomerTest() throws Exception {
        mockMvc.perform(post("/customer/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("customerId", "1")
                .param("customerName", "Brandon Davis")
                .param("customerEmail", "bdave@example.com")
                .param("customerPhoneNumber", "678-999-8212"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer"));
    }

    // Test deleting a customer
    @Test
    void deleteCustomerTest() throws Exception {
        int customerId = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName("John Doe");

        given(customerService.findCustomerByCustomerId(customerId)).willReturn(customer);
        doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/customer/{customerId}", customerId))
                .andExpect(status().isNoContent());
    }
}