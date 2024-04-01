package com.cmsc495.hotelmanagementapp;
/*
 * File: HotelmanagementappApplication.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/HotelmanagementappApplication.java
 * Package: com.cmsc495.hotelmanagementapp
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-23
 * Last Modified: 2024-03-23
 * Description: This file contains the main application class (App) for the Hotel Management System, 
 *              responsible for running and managing the hotelmanagementapp.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HotelmanagementappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelmanagementappApplication.class, args);
	}

}
