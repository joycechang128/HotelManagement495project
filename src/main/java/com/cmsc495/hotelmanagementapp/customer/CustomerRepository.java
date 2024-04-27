package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerRepository.java
  * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Mrigank Gupta, Chia-Yu(Joyce) Chang
 * Created: 2024-04-11
 * Last Modified: 2024-04-27
 * Description: This repository interface provides methods for accessing and manipulating data in the database related to customers. 
 *              It defines CRUD operations for customer entities, allowing interaction with the underlying data store.
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>  {

	Customer save(Customer customer);
	
	Optional<Customer> findCustomerByCustomerId(int customerId);
	
	List<Customer> findAll();
	
	void deleteCustomerByCustomerId(int customerId);
	
}
