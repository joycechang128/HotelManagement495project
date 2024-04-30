package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationController.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-30 
 * Description: This controller manages the operations related to reservations in the hotel reservation system.
 *              It provides mappings for creating, updating, and deleting reservation data of customers, 
 *              as well as retrieving information about all reservation.
 */

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmsc495.hotelmanagementapp.customer.Customer;
import com.cmsc495.hotelmanagementapp.customer.CustomerService;
import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomService;

@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
    private RoomService roomService;
	
	/* The value="reservation" will map to the <a href="reservation"> tag in html -> the reservation management button 
	 * This method retrieves reservations data from the database and stores in the model for rendering in the view */
	@GetMapping("/reservation")
	public String getAllReservations(Model model) {
		List<Reservation> reservations = reservationService.getAllReservations();
		model.addAttribute("reservations", reservations);
		return "reservation";
	}
	
	/* Method to display new-reservation.html, the form to input data for new reservation.
	 * Retrieve customers and rooms data for data selection */
	@GetMapping("/reservation/new")
	public String showCreateReservationForm(@RequestParam(name = "roomNumber", required = false) Integer roomNumber, @RequestParam(name = "checkInDate", required = false) Date checkInDate, Model model) {
		Reservation reservation = new Reservation();
		
		// to display customers & rooms selection
		List<Customer> customers = customerService.getAllCustomersSortedByNames();
		List<Room> rooms = roomService.getAllRooms();
		
		// Initialize available dates map
	    List<LocalDate> availableCheckInDates = new ArrayList<>();
	    List<LocalDate> availableCheckOutDates = new ArrayList<>();
	    
	    // If a room is selected, fetch available check-in dates for that room
	    if (roomNumber != null) {
	    	availableCheckInDates = reservationService.findAvailableDatesForRoom(roomNumber, true, null);
	    }
	    
	    
	    // If a check-in date is selected, fetch available check-out dates based on check-in date
	    if (checkInDate != null) {
            LocalDate localCheckInDate = Instant.ofEpochMilli(checkInDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	        availableCheckOutDates = reservationService.findAvailableDatesForRoom(roomNumber, false, localCheckInDate);
	    }
	    
		model.addAttribute("reservation", reservation);
		model.addAttribute("customers", customers);
		model.addAttribute("rooms", rooms);
		model.addAttribute("roomNumber", roomNumber);
	    model.addAttribute("availableCheckInDates", availableCheckInDates);
	    model.addAttribute("availableCheckOutDates", availableCheckOutDates);
	    
		return "new-reservation";
	}
	
	/* This method retrieves a list of available check-in dates for the specified room,
	 * to populate the selection of check-in dates in the frontend.
	 * @param roomNumber The ID of the room for which available check-in dates are to be retrieved
	 * return A ResponseEntity containing a list of LocalDate objects representing available check-in dates. */
	@GetMapping("/getAvailableCheckInDates")
	public ResponseEntity<List<LocalDate>> getAvailableCheckInDates(@RequestParam("roomNumber") Integer roomNumber) {
	    List<LocalDate> availableCheckInDates = reservationService.findAvailableDatesForRoom(roomNumber, true, null);
	    return ResponseEntity.ok(availableCheckInDates);
	}
	
	/* This method retrieves a list of available check-out dates for the specified room,
	 * to populate the selection of check-out dates based on the selected check-in date in the frontend.
	 * @param roomNumber The ID of the room for which available check-in dates are to be retrieved
	 * @param checkInDate The selected check-in date for which available check-out dates are to be retrieved
	 * return A ResponseEntity containing a list of LocalDate objects representing available check-out dates. */
	@GetMapping("/getAvailableCheckOutDates")
	public ResponseEntity<List<LocalDate>> getAvailableCheckOutDates(@RequestParam("roomNumber") Integer roomNumber, @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate) {
		List<LocalDate> availableCheckOutDates = reservationService.findAvailableDatesForRoom(roomNumber, false, checkInDate);
	    return ResponseEntity.ok(availableCheckOutDates);
	}
	
	/* Method to save new reservation after new data has been input into new-reservation.html
	@PostMapping("/save")
	public String saveReservation(@ModelAttribute("reservation") Reservation reservation) {
		reservationService.makeReservation(reservation);
		return "redirect:/reservation";
	}*/
	
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
