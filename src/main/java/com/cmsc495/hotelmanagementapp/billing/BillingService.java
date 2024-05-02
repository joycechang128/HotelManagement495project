package com.cmsc495.hotelmanagementapp.billing;
/*
 * File: BillingService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/billing/BillingService.java
 * Package: com.cmsc495.hotelmanagementapp.billing
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-05-02
 * Description: This service class contains business logic operations related to billings in the hotel management system.  
 *              It provides methods for handling CRUD operations on billing data, as well as additional business logics.
 */

import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsc495.hotelmanagementapp.reservation.Reservation;

import jakarta.transaction.Transactional;

@Service
public class BillingService {
	
	@Autowired
	private BillingRepository billingRepository;
	
	public List<Billing> getAllBillings() {
		return billingRepository.findAll();
	}
	
	/* This method computes the payment amount for a reservation, 
	 * which is determined by various room types and the number of reserved days */
	public double getCalculatedAmount(Reservation reservation) {
		double amount = 0;
		
		String roomType = reservation.getRoom().getRoomType();
		/* Calculate days interval for reserved days,
		 * convert java.sql.Date to java.util.Date in order to display accurately */
		Date checkInDate = new Date(reservation.getCheckInDate().getTime());
		Date checkOutDate = new Date(reservation.getCheckOutDate().getTime());
		long reservedDays = ChronoUnit.DAYS.between(checkInDate.toInstant(), checkOutDate.toInstant());
	    
		// calculate amount based on room type and check-in/check-out date
		if (roomType.equals("Single")) {
			amount = 145.8 * reservedDays;
		} else if (roomType.equals("Double")) {
			amount = 180.72 * reservedDays;
		} else if (roomType.equals("Triple")) {
			amount = 223.5 * reservedDays;
		} else if (roomType.equals("Family")) {
			amount = 311.2 * reservedDays;
		}
		
		// Format the amount to two decimal places
		DecimalFormat df = new DecimalFormat("#.##");
		amount = Double.parseDouble(df.format(amount));
	    
		return amount;
	}
	
	/* This method creates a new billing record in a system.
	 * The payment status will typically be set to "unpaid" for new billing records */
	public void createBilling(Billing billing) {
		billingRepository.save(billing);
	}
	
	/* This method updates a billing record in a system */
	@Transactional
	public Billing updateBilling(Billing billing) {
		return billingRepository.save(billing);
	}
}
