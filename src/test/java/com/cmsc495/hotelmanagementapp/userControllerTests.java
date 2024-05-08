package com.cmsc495.hotelmanagementapp;

/*
* File: userControllerTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/userControllerTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the User Controller.
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.cmsc495.hotelmanagementapp.user.UserController;
import com.cmsc495.hotelmanagementapp.user.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Test displaying the login page
    @Test
    void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // Test successful login
    @Test
    void testSuccessfulLogin() throws Exception {
        String username = "admin";
        String password = "password";
        UserDetails mockUserDetails = mock(UserDetails.class);

        when(userService.loadUserByUsername(username)).thenReturn(mockUserDetails);

        mockMvc.perform(post("/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/main"));
    }

    // Test failed login
    @Test
    void testFailedLogin() throws Exception {
        String username = "wrong";
        String password = "pass";

        when(userService.loadUserByUsername(username)).thenThrow(new UsernameNotFoundException("User not found"));

        mockMvc.perform(post("/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    // Test logout functionality
    @Test
    void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }
}