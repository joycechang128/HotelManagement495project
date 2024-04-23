package com.cmsc495.hotelmanagementapp.reservation;
/*
 * File: ReservationRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/reservation/ReservationRepository.java
 * Package: com.cmsc495.hotelmanagementapp.reservation
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-19
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	Reservation save(Reservation reservation);
	
	// below @Query has the same function as "findReservationById(int reservationId)"
	// @Query("SELECT r FROM Reservation r WHERE r.reservationId = ?")
	Optional<Reservation> findReservationByReservationId(int reservationId);
	
	List<Reservation> findAll();
	
	void deleteReservationByReservationId(int reservationId);
}
