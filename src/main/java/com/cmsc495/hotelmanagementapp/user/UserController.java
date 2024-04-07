package com.cmsc495.hotelmanagementapp.user;
/*
 * File: UserController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/user/UserController.java
 * Package: com.cmsc495.hotelmanagementapp.user
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-25
 * Last Modified: 2024-03-30
 * Description: This file contains the Java controller class for the Hotel Management System's login page (login.html), 
 *              responsible for handling user requests and managing interactions with the login page.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/login")
	public String showLoginPage() {
		// return login.html
		return "login";
	}
	
	
	@PostMapping("/login")
	public String showMainPage(@RequestParam String username, @RequestParam String password) {
        try {
        	// Attempt to authenticate using the provided username and password
            UserDetails userDetails = service.loadUserByUsername(username);
            // If authentication succeeds, redirect to the main page
            /* Using "redirect:" allows the browser to perform a new GET request rather than 
			 * just redirecting to the /login page while keeping the URL as /main
			 */
            return "redirect:/main";
        } catch (UsernameNotFoundException e) {
        	// If authentication fails, redirect back to the login page
            return "redirect:/login";
        }
    }
	
	// The value="logout" will map to the <a href="logout"> tag in main.html -> the logout button in main page
	@GetMapping("/logout")
	public String showLogoutPage() {
		return "redirect:/login";
	}
}
