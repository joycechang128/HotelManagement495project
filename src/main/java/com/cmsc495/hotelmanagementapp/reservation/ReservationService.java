package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationService.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-19
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}
	
	public void save(Reservation reservation) {
		reservationRepository.save(reservation);
	}
	
	public void makeReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }
	
	public Optional<Reservation> findReservationById(int reservationId) {
        return reservationRepository.findReservationByReservationId(reservationId);
    }
	
	public void deleteReservationById(int reservationId) {
        reservationRepository.deleteReservationByReservationId(reservationId);
    }
}
