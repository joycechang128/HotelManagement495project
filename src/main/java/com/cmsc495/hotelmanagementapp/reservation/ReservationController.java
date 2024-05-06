package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationController.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-04
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmsc495.hotelmanagementapp.customer.Customer;
import com.cmsc495.hotelmanagementapp.customer.CustomerRepository;
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
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	
	/* The value="reservation" will map to the <a href="reservation"> tag in html -> the reservation management button 
	 * This method retrieves reservations data from the database and stores in the model for rendering in the view */
	@GetMapping("/reservation")
	public String getAllReservations(Model model) {
		List<Reservation> reservations = reservationService.getAllReservations();
		model.addAttribute("reservations", reservations);
		
		// Displaying summary in the reservation page
		int totalNumOfReservations = reservationRepository.countAllReservations();
		int totalNumOfCustomers = customerRepository.countAllCustomers();
		model.addAttribute("totalNumOfReservations", totalNumOfReservations);
		model.addAttribute("totalNumOfCustomers", totalNumOfCustomers);
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
	public String showEditReservationForm(@PathVariable("reservationId") int reservationId,
			@RequestParam(name = "roomNumber", required = false) Integer roomNumber,
			@RequestParam(name = "checkInDate", required = false) Date checkInDate, 
			@RequestParam(name = "checkOutDate", required = false) Date checkOutDate, 
			Model model) {
		// Retrieve the existing reservation by ID
		Reservation reservation = reservationService.findReservationById(reservationId).orElse(null);
		
		// Retrieve all rooms for selection
		List<Room> rooms = roomService.getAllRooms();
		// Initialize available dates list
		List<LocalDate> availableCheckInDatesForEdit = new ArrayList<>();
		List<LocalDate> availableCheckOutDatesForEdit = new ArrayList<>();
				
		// If a room is selected, fetch available check-in dates for that room
		if (roomNumber != null) {
			availableCheckInDatesForEdit = reservationService.findAvailableDatesForReservationEdit(roomNumber, true, reservationId, null);
		}
		// If a check-in date is selected, fetch available check-out dates based on check-in date
		if (checkInDate != null) {
			LocalDate localCheckInDate = Instant.ofEpochMilli(checkInDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
			availableCheckOutDatesForEdit = reservationService.findAvailableDatesForReservationEdit(roomNumber, false, reservationId, localCheckInDate);
		}
	    
		model.addAttribute("reservation", reservation);
		model.addAttribute("rooms", rooms);
		model.addAttribute("roomNumber", roomNumber);
		model.addAttribute("checkInDate", checkInDate);
		model.addAttribute("checkOutDate", checkOutDate);
		model.addAttribute("availableCheckInDatesForEdit", availableCheckInDatesForEdit);
		model.addAttribute("availableCheckOutDatesForEdit", availableCheckOutDatesForEdit);
		
		return "edit-reservation";
	}
	
	/* This method retrieves a list of available check-in dates for editing the specified room. */
	@GetMapping("/getAvailableCheckInDatesForEdit")
	public ResponseEntity<List<LocalDate>> getAvailableCheckInDatesForEdit(@RequestParam("roomNumber") Integer roomId,
			@RequestParam("reservationId") int reservationId) {
		List<LocalDate> availableCheckInDatesForEdit = reservationService.findAvailableDatesForReservationEdit(roomId, true, reservationId, null);
		return ResponseEntity.ok(availableCheckInDatesForEdit);
	}
	
	/* This method retrieves a list of available check-out dates for editing the specified room. */
	@GetMapping("/getAvailableCheckOutDatesForEdit")
	public ResponseEntity<List<LocalDate>> getAvailableCheckOutDatesForEdit(@RequestParam("roomNumber") Integer roomId, 
			@RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
			@RequestParam("reservationId") int reservationId) {
		List<LocalDate> availableCheckOutDatesForEdit = reservationService.findAvailableDatesForReservationEdit(roomId, false, reservationId, checkInDate);
		return ResponseEntity.ok(availableCheckOutDatesForEdit);
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

	/* This method handles the deletion of a reservation and its associated billing information.
	 * It executes deletion operations within a transaction to ensure data consistency
	 * If the deletion is successful, the transaction is committed and a success message is displayed,
	 * if any exception occurs during the deletion process, the transaction is rolled back,
	 * an error message is logged, and the user is redirected back to the Reservation page with an error message.
	 * Return a redirect to the Reservation page with a success or error message based on the deletion outcome. */
	@GetMapping("/reservation/delete/{reservationId}")
	public String deleteReservation(@PathVariable(name="reservationId") int reservationId) {
		TransactionStatus status = null;
		try {
			// Execute deletion operations within a transaction
			status = transactionManager.getTransaction(new DefaultTransactionDefinition());
			// Delete the reservation and the associated billing, billing should be deleted before reservation
			billingService.deleteBillingById(reservationId);
			reservationService.deleteReservationById(reservationId);

			// Commit the transaction
			transactionManager.commit(status);
	        
			String successMessage = "The reservation and its associated billing have been deleted successfully! Reservation ID: " + reservationId;

			return "redirect:/reservation?message=" + successMessage.replace(" ", "+");
		} catch (Exception e) {
			// Rollback the transaction if an exception occurs
			if (status != null && !status.isCompleted()) {
				transactionManager.rollback(status);
			}
			// Log the exception and redirect back to the Reservation page with an error message
			Logger logger = LoggerFactory.getLogger(ReservationController.class);
			logger.error("Failed to delete reservation with ID: " + reservationId, e);
			return "redirect:/reservation?error=Failed+to+delete+reservation.";
		}
	}
}
