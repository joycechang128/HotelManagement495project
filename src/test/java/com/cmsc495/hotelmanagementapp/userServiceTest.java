package com.cmsc495.hotelmanagementapp;

/*
* File: userServiceTest.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/userServiceTest.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the User Service.
*/

import static org.junit.jupiter.api.Assertions.*;

import com.cmsc495.hotelmanagementapp.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    // Test loading a user by username where the user exists
    @Test
    void testLoadUserByUsername_UserExists() {
        String username = "admin";

        // Attempt to load the user details for a known username
        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails, "UserDetails should not be null for existing user.");
        assertEquals(username, userDetails.getUsername(), "The username should match the one requested.");
    }

    // Test loading a user by username where the user does not exist
    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        String username = "unknown";

        // Expect an exception when the username does not exist
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username),
                     "Should throw UsernameNotFoundException for an unknown user.");
    }
}