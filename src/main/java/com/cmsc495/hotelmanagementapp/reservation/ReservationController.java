package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationController.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-19
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	/* The value="reservation" will map to the <a href="reservation"> tag in html -> the reservation management button 
	 * This method retrieves reservations data from the database and stores in the model for rendering in the view
	 */
	@GetMapping("/reservation")
	public String getAllReservations(Model model){
		List<Reservation> reservations = reservationService.getAllReservations();
		model.addAttribute("reservations", reservations);
		return "reservation";
	}
	/*
	@PostMapping
	public void createReservation(@RequestBody Reservation reservation) {
		reservationService.makeReservation(reservation);
	}*/
}
