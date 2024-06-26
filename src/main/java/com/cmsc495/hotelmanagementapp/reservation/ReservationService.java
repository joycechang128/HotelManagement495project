package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationService.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-06
 * Description: This service class contains business logic operations related to reservations in the hotel management system.  
 *              It provides methods for handling CRUD operations on reservation data, as well as additional business logics.
 */

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsc495.hotelmanagementapp.customer.Customer;
import com.cmsc495.hotelmanagementapp.customer.CustomerRepository;
import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
	
	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}
	
	/* This method finds available check-in dates & check-out dates for the specified room 
	 * isCheckInDates=true -> check-in date, isCheckInDates=false -> check-out date 
	 * return a list of LocalDate objects */
	public List<LocalDate> findAvailableDatesForRoom(int roomId, boolean isCheckInDates, LocalDate selectedCheckInDate) {
		List<LocalDate> availableDates = new ArrayList<>();
        
		// Get the room by its ID
		Room room = roomRepository.findById(roomId);
		// Get all reservations for the specified room
		List<Reservation> reservations = reservationRepository.findByRoom(room);
        
		// Log the reservations for debugging
		StringBuilder reservationDetails = new StringBuilder();
		for (Reservation reservation : reservations) {
			reservationDetails.append(reservation.toString()).append("\n");
		}
		logger.info("\nReservations for room {}: \n{}", roomId, reservationDetails.toString());
        
		// Iterate over each day within the next two months
		LocalDate today = LocalDate.now();
		LocalDate twoMonthsLater = today.plusMonths(2);
		LocalDate currentDate = today;
		while (!currentDate.isAfter(twoMonthsLater)) {
			// Check if the current date is booked
			boolean isBooked = false;
			for (Reservation reservation : reservations) {
				Date checkInDate = reservation.getCheckInDate();
				Date checkOutDate = reservation.getCheckOutDate();
                
				// Convert Date (java.util.Date) objects to LocalDate (java.time.LocalDate) objects
				LocalDate localCheckInDate = Instant.ofEpochMilli(checkInDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate localCheckOutDate = Instant.ofEpochMilli(checkOutDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                
				// logger.info("Checking reservation for room: {} | Check-in: {} | Check-out: {}", reservation.getRoomNumber(), localCheckInDate, localCheckOutDate);
                
				// Check if calculating check-in date or check-out date
				if (isCheckInDates) {
					if (currentDate.isEqual(localCheckInDate) || currentDate.isAfter(localCheckInDate) && currentDate.isBefore(localCheckOutDate)) {
						isBooked = true;
						break;
					}
				} else {
					// Find the next booked date after the selected check-in date
					LocalDate nextBookedDate = null;
					for (Reservation res : reservations) {
						LocalDate resCheckInDate = Instant.ofEpochMilli(res.getCheckInDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
						if (resCheckInDate.isAfter(selectedCheckInDate) && (nextBookedDate == null || resCheckInDate.isBefore(nextBookedDate))) {
							nextBookedDate = resCheckInDate;
						}
					}
					// Check if the current date is after the selected check-in date and before the next booked date
					if ((nextBookedDate != null && currentDate.isAfter(nextBookedDate)) || 
                			currentDate.isBefore(selectedCheckInDate.plusDays(1))) {
						isBooked = true;
						break;
					}
				}
			}
			// If the current date is not booked, add it to the list of available dates
			if (!isBooked) {
				availableDates.add(currentDate);
			}
			// Move the date forward by one day
			currentDate = currentDate.plusDays(1);
		}
		return availableDates;
	}
	
	/* This method finds available check-in dates & check-out dates for editing a specific reservation,
	 * considering the existing available dates provided by findAvailableDatesForRoom, which is used in creating a reservation.
	 * isCheckInDates=true -> check-in date, isCheckInDates=false -> check-out date 
	 * return a list of LocalDate objects */
	public List<LocalDate> findAvailableDatesForReservationEdit(int roomId, boolean isCheckInDates, int reservationId, LocalDate selectedCheckInDate) {
	    // Get the existing available dates using findAvailableDatesForRoom method
		List<LocalDate> availableDates = new ArrayList<>();

	    // Retrieve the reservation by its ID
	    Reservation reservation = reservationRepository.findReservationByReservationId(reservationId).orElse(null);
	    
		logger.info("\nReservations for room {}: \n{}", roomId, reservation.toString());

	    
	    // Add the booked dates of the specified reservation to the available dates list
	    LocalDate checkInDate = Instant.ofEpochMilli(reservation.getCheckInDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate checkOutDate = Instant.ofEpochMilli(reservation.getCheckOutDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	    
		logger.info("Checking reservation for room: {} | Check-in: {} | Check-out: {}", reservation.getRoomNumber(), checkInDate, checkOutDate);

		
	    if (isCheckInDates) {
	    	// Get available check-in dates for the room, add the booked check-in date range
	        availableDates = findAvailableDatesForRoom(roomId, true, null);
	        availableDates.addAll(checkInDate.datesUntil(checkOutDate).collect(Collectors.toList()));
	    } else {
	    	// Get available check-out dates for the room, add the booked check-out date range
	        availableDates = findAvailableDatesForRoom(roomId, false, selectedCheckInDate);
	        availableDates.addAll(checkInDate.plusDays(1).datesUntil(checkOutDate).collect(Collectors.toList()));
	    }
	    
	    // Sort and return the updated available dates list
	    Collections.sort(availableDates);
	    return availableDates;
	}
	
	/* This method creates a new reservation record in a system */
	public void createReservation(Reservation reservation) {
		reservationRepository.save(reservation);
	}
	
	/* This method updates a reservation record in a system */
	@Transactional
	public Reservation updateReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	public Optional<Reservation> findReservationById(int reservationId) {
		return reservationRepository.findReservationByReservationId(reservationId);
	}
	
	public void deleteReservationById(int reservationId) {
		reservationRepository.deleteReservationByReservationId(reservationId);
	}
	
	public List<Reservation> getReservationsByRoomId(int roomId) {
		Room room = roomRepository.findById(roomId);
		return reservationRepository.findByRoom(room);
	}
	
	public List<Reservation> getReservationsByCustomerId(int customerId) {
		Customer customer = customerRepository.findCustomerByCustomerId(customerId);
        return reservationRepository.findByCustomer(customer);
    }
}
