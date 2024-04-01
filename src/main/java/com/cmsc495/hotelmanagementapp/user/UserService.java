package com.cmsc495.hotelmanagementapp.user;
/*
 * File: UserService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/user/UserService.java
 * Package: com.cmsc495.hotelmanagementapp.user
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-29
 * Last Modified: 2024-03-30
 * Description: This file contains the Java service class for managing user authentication and authorization within the Hotel Management System, 
 *              responsible for handling user requests related to login and logout functionality.
 */

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	/* Implementing the UserDetailsService interface allows UserService to provide user details 
	 * required for authentication and authorization processes, ensuring compatibility with 
	 * Spring Security configurations.
	 */

	@Override
	public UserDetails loadUserByUsername(String username) {
	    // check if the username="admin"
	    if ("admin".equals(username)) {
	    	// Return user details of the username and password
	        return User.withUsername("admin")
	                   .password("password")
	                   .build();
	    } else {
	    	throw new UsernameNotFoundException("User not found with username: " + username);
	    }
	}
}