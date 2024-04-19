package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomService.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * 				...
 */

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
}
