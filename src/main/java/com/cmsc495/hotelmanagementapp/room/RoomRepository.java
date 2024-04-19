package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomRepository.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	List<Room> findAll();
}
