package com.cmsc495.hotelmanagementapp;

/*
* File: reservationControllerTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/reservationControllerTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Reservation Controller.
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.cmsc495.hotelmanagementapp.billing.*;
import com.cmsc495.hotelmanagementapp.reservation.*;
import com.cmsc495.hotelmanagementapp.customer.*;
import com.cmsc495.hotelmanagementapp.room.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private BillingService billingService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private CustomerRepository customerRepository;

    // Test retrieving all reservations and displaying on the model
    @Test
    void testGetAllReservations() throws Exception {
        List<Reservation> reservations = new ArrayList<>();
        when(reservationService.getAllReservations()).thenReturn(reservations);
        when(reservationRepository.countAllReservations()).thenReturn(10);
        when(customerRepository.countAllCustomers()).thenReturn(5);

        mockMvc.perform(get("/reservation"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation"))
                .andExpect(model().attributeExists("reservations"))
                .andExpect(model().attribute("totalNumOfReservations", 10))
                .andExpect(model().attribute("totalNumOfCustomers", 5));
    }

    // Test displaying the form for creating a new reservation
    @Test
    void testShowCreateReservationForm() throws Exception {
        mockMvc.perform(get("/reservation/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new-reservation"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attributeExists("rooms"));
    }

    // Test saving a new reservation
    @Test
    void testSaveReservation() throws Exception {
        mockMvc.perform(post("/reservation/save")
                .param("customerId", "1")
                .param("roomId", "1")
                .param("checkInDate", "2024-05-10")
                .param("checkOutDate", "2024-05-12")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reservation"));
    }

    // Test showing the edit reservation form
    @Test
    void testShowEditReservationForm() throws Exception {
        when(reservationService.findReservationById(anyInt())).thenReturn(java.util.Optional.of(new Reservation()));

        mockMvc.perform(get("/reservation/edit/{reservationId}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-reservation"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(model().attributeExists("rooms"));
    }

    // Test updating an existing reservation
    @Test
    void testUpdateReservation() throws Exception {
        mockMvc.perform(post("/reservation/edit")
                .param("reservationId", "1")
                .param("roomId", "1")
                .param("checkInDate", "2024-05-10")
                .param("checkOutDate", "2024-05-12")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reservation"));
    }

    // Test deleting a reservation
    @Test
    void testDeleteReservation() throws Exception {
        mockMvc.perform(get("/reservation/delete/{reservationId}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reservation"));
    }
}