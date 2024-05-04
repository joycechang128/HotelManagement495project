package com.cmsc495.hotelmanagementapp.customer;
/*
 * File: CustomerRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/customer/CustomerRepository.java
 * Package: com.cmsc495.hotelmanagementapp.customer
 * Author: Chia-Yu(Joyce) Chang, Mrigank Gupta
 * Created: 2024-04-11
 * Last Modified: 2024-05-04
 * Description: This repository interface provides methods for accessing and manipulating data in the database related to customers. 
 *              It defines CRUD operations for customer entities, allowing interaction with the underlying data store.
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>  {

	Customer save(Customer customer);
	
	Customer findByCustomerName(String customerName);
	
	Customer findCustomerByCustomerId(int customerId);
	
	List<Customer> findAll();
	
	void deleteCustomerByCustomerId(int customerId);
	
	@Query("SELECT COUNT(c) FROM Customer c")
    int countAllCustomers();
	
}
