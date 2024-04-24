package com.cmsc495.hotelmanagementapp.main;
/*
 * File: MainController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/main/MainController.java
 * Package: com.cmsc495.hotelmanagementapp.main
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-28
 * Last Modified: 2024-04-23
 * Description: This file contains the Java controller class for the Hotel Management System's main page (main.html), 
 * 				responsible for handling user requests and managing interactions with the main page.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	// return main.html
	@GetMapping("/main")
	public String showMainPage() {
		return "main";
	}

	// The value="customer" will map to the <a href="customer"> tag in main.html -> the customer management button
	@GetMapping("/customer")
	public String customerPage() {
		return "customer";
	}
	
	// The value="billing" will map to the <a href="billing"> tag in main.html -> the billing management button
	@GetMapping("/billing")
	public String billingPage() {
		return "billing";
	}
	
	// The value="housekeeping" will map to the <a href="housekeeping"> tag in main.html -> the housekeeping management button
	@GetMapping("/housekeeping")
	public String housekeepingPage() {
		return "housekeeping";
	}
	
	// The value="visualreport" will map to the <a href="visualreport"> tag in main.html -> the visualreport management button
	@GetMapping("/visualreport")
	public String visualReportPage() {
		return "visualreport";
	}
}
