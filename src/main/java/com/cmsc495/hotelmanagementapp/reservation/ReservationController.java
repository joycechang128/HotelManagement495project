package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationController.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-03
 * Description: This controller manages the operations related to reservations in the hotel reservation system.
 *              It provides mappings for creating, updating, and deleting reservation data of customers, 
 *              as well as retrieving information about all reservation.
 */

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmsc495.hotelmanagementapp.customer.Customer;
import com.cmsc495.hotelmanagementapp.customer.CustomerService;
import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomService;
import com.cmsc495.hotelmanagementapp.billing.Billing;
import com.cmsc495.hotelmanagementapp.billing.BillingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private BillingService billingService;
	
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
	public String showCreateReservationForm(@RequestParam(name = "roomNumber", required = false) Integer roomNumber, 
			@RequestParam(name = "checkInDate", required = false) Date checkInDate, Model model) {
		Reservation reservation = new Reservation();
		
		// to display customers & rooms selection
		List<Customer> customers = customerService.getAllCustomersSortedByNames();
		List<Room> rooms = roomService.getAllRooms();
		
		// Initialize available dates list
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
	 * return a ResponseEntity containing a list of LocalDate objects representing available check-in dates. */
	@GetMapping("/getAvailableCheckInDates")
	public ResponseEntity<List<LocalDate>> getAvailableCheckInDates(@RequestParam("roomNumber") Integer roomId) {
		List<LocalDate> availableCheckInDates = reservationService.findAvailableDatesForRoom(roomId, true, null);
		return ResponseEntity.ok(availableCheckInDates);
	}
	
	/* This method retrieves a list of available check-out dates for the specified room,
	 * to populate the selection of check-out dates based on the selected check-in date in the frontend.
	 * @param roomNumber The ID of the room for which available check-in dates are to be retrieved
	 * @param checkInDate The selected check-in date for which available check-out dates are to be retrieved
	 * return a ResponseEntity containing a list of LocalDate objects representing available check-out dates. */
	@GetMapping("/getAvailableCheckOutDates")
	public ResponseEntity<List<LocalDate>> getAvailableCheckOutDates(@RequestParam("roomNumber") Integer roomId, @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate) {
		List<LocalDate> availableCheckOutDates = reservationService.findAvailableDatesForRoom(roomId, false, checkInDate);
		return ResponseEntity.ok(availableCheckOutDates);
	}
	
	/* This method is responsible for saving a new reservation into the system.
	 * It retrieves customer and room data based on the input in new-reservation.html,
	 * creates a new reservation & associated billing, and redirects the user to the reservation page.
	 * return a redirection to the reservation page. */
	@PostMapping("/reservation/save")
	public String saveReservation(@ModelAttribute("reservation") Reservation reservation,
			@RequestParam("customerName") Integer customerId, @RequestParam("roomNumber") Integer roomId,
			@RequestParam("checkInDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate, 
			@RequestParam("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate) {
		// for debugging, showing the customerId & roomId retrieved
		Logger logger = LoggerFactory.getLogger(ReservationController.class);
		logger.info("create page customerId recieved: " + customerId);
		logger.info("create page roomId recieved: " + roomId);

		// Find customer and room data based on inputs
		Customer customer = customerService.findCustomerById(customerId);
		Room room = roomService.getRoomById(roomId);
	    
		// Set reservation details
		reservation = new Reservation(customer, room, null, checkInDate, checkOutDate);
		// Create and save a reservation
		reservationService.createReservation(reservation);
		
		// Automatically create a corresponding billing, set payment status to unpaid when initially created
		Billing billing = new Billing(customer, null, "Unpaid");
		billingService.createBilling(billing);
	    
		// Update Reservation's BillingID, can only be set after creating reservation
		reservation.setBilling(billing);
		reservationService.updateReservation(reservation);
		// Update Billing's ReservationID, can only be set after creating billing
		billing.setReservation(reservation);
		billingService.updateBilling(billing);
		
		return "redirect:/reservation?message=The+reservation+has+been+created+successfully!" + 
		"+Reservation+ID%3A+" + reservation.getReservationId() +
		",+Billing+ID%3A+" + reservation.getBilling().getBillingId();
	}
	
	/* Method to edit reservation, opening edit-reservation.html */
	@GetMapping("/reservation/edit/{reservationId}")
	public String showEditReservationPage(@PathVariable("reservationId") int reservationId,
			@RequestParam(name = "roomNumber", required = false) Integer roomNumber,
			@RequestParam(name = "checkInDate", required = false) Date checkInDate, 
			@RequestParam(name = "checkOutDate", required = false) Date checkOutDate, 
			Model model) {
		// Retrieve the existing reservation by ID
		Reservation reservation = reservationService.findReservationById(reservationId).orElse(null);
		
		// Retrieve all rooms for selection
		List<Room> rooms = roomService.getAllRooms();
		// Initialize available dates list
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
		model.addAttribute("rooms", rooms);
		model.addAttribute("roomNumber", roomNumber);
		model.addAttribute("checkInDate", checkInDate);
		model.addAttribute("checkOutDate", checkOutDate);
		model.addAttribute("availableCheckInDates", availableCheckInDates);
		model.addAttribute("availableCheckOutDates", availableCheckOutDates);
		
		return "edit-reservation";
	}
	
	/* This method is responsible for updating an existing reservation in the system.
	 * It retrieves customer and room data based on the input in edit-reservation.html,
	 * updates the existing reservation & associated billing, and redirects the user to the reservation page. */
	@PostMapping("/reservation/edit")
	public String updateReservation(@ModelAttribute("reservation") Reservation reservation,
			@RequestParam("roomNumber") Integer roomId,
			@RequestParam("checkInDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
			@RequestParam("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate) {
		// for debugging, showing the roomId retrieved
		Logger logger = LoggerFactory.getLogger(ReservationController.class);
		logger.info("edit page roomId received: " + roomId);
		
		// Find room data based on input
		Room room = roomService.getRoomById(roomId);

		// Retrieve existing reservation from the database
		Reservation existingReservation = reservationService.findReservationById(reservation.getReservationId()).orElse(null);
		
		// Get billing amount before updating the reservation
		Billing originalBilling = billingService.findBillingById(existingReservation.getBilling().getBillingId());
		double originalPaymentAmount = originalBilling.getAmount();
				
		// Set updated reservation details
		existingReservation.setRoom(room);
		existingReservation.setCheckInDate(checkInDate);
		existingReservation.setCheckOutDate(checkOutDate);
		
		// Update the reservation
		reservationService.updateReservation(existingReservation);
		
		// Get billing amount after updating the reservation
		Billing updatedBilling = billingService.findBillingById(existingReservation.getBilling().getBillingId());
		double updatedPaymentAmount = updatedBilling.getAmount();
		
		// Date formatter with date format: yyyy-MM-dd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Convert java.util.Date to java.time.LocalDate
		LocalDate checkInLocalDate = existingReservation.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate checkOutLocalDate = existingReservation.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// Format LocalDate using the formatter to get the date part only as a string
		String formattedCheckInDate = checkInLocalDate.format(formatter);
		String formattedCheckOutDate = checkOutLocalDate.format(formatter);

		return "redirect:/reservation?message=The+reservation+has+been+updated+successfully!" + 
		"+Reservation+ID%3A+" + existingReservation.getReservationId() +
		"+Room: " + existingReservation.getRoom().getRoomNumber() +
		",+Check-in Date: " + formattedCheckInDate +
		",+Check-out Date: " + formattedCheckOutDate +
		",+Payment+Amount%3A+Original%3A+" + originalPaymentAmount + ",+Updated%3A+" + updatedPaymentAmount;
	}

	/* Method to delete reservation */
	@GetMapping("/delete/{reservationId}")
	public String deleteReservation(@PathVariable(name="reservationId") int reservationId) {
		reservationService.deleteReservationById(reservationId);
		return "redirect:/reservation";
	}
}
