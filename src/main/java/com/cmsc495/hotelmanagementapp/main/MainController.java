package com.cmsc495.hotelmanagementapp.main;
/*
 * File: MainController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/main/MainController.java
 * Package: com.cmsc495.hotelmanagementapp.main
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-28
 * Last Modified: 2024-04-27
 * Description: This file contains the Java controller class for the Hotel Management System's main page (main.html), 
 *              responsible for handling user requests and managing interactions with the main page.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	// return main.html
	@GetMapping(value = {"/", "/main"})
	public String showMainPage() {
		return "main";
	}
}
