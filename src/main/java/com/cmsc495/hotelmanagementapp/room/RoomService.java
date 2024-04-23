package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomService.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-04-16
 * Description: This file contains...
 * 				...
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

}


