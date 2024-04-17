package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerRepository.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>  {

	List<Customer> findAll();
}
