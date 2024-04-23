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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomService;

@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
    private RoomService roomService;
	
	/* The value="reservation" will map to the <a href="reservation"> tag in html -> the reservation management button 
	 * This method retrieves reservations data from the database and stores in the model for rendering in the view
	 */
	@GetMapping("/reservation")
	public String getAllReservations(Model model) {
		List<Reservation> reservations = reservationService.getAllReservations();
		model.addAttribute("reservations", reservations);
		return "reservation";
	}
	
	/* Method to display new-reservation.html, the form to input data for new reservation */
	@GetMapping("/reservation/new")
	public String showCreateReservationForm(Model model) {
		Reservation reservation = new Reservation();
		//List<Room> rooms = roomService.getAllRooms();
		model.addAttribute("reservation", reservation);
		//model.addAttribute("rooms", rooms);
		return "new-reservation";
	}
	
	/* Method to save new reservation after new data has been input into reservation-new.html */
	@PostMapping("/save")
	public String saveReservation(@ModelAttribute("reservation") Reservation reservation) {
		reservationService.makeReservation(reservation);
		return "redirect:/reservation";
	}
	
	/* Method to edit reservation, opening edit-reservation.html */
	@GetMapping("/edit/{reservationId}")
	public ModelAndView showEditReservationPage(@PathVariable(name="reservationId") int reservationId) {
		ModelAndView editReservation = new ModelAndView("edit-reservation");
		Reservation reservation = reservationService.findReservationById(reservationId).orElse(null);
		editReservation.addObject("reservation", reservation);
		return editReservation;
	}
	
	/* Method to delete reservation */
	@GetMapping("/delete/{reservationId}")
	public String deleteReservation(@PathVariable(name="reservationId") int reservationId) {
		reservationService.deleteReservationById(reservationId);
		return "redirect:/reservation";
	}
}
