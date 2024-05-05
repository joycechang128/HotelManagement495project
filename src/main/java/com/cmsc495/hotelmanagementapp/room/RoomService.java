package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomService.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-05-03
 * Description: This file contains...
 * ...
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(Room room) {
        Room existingRoom = roomRepository.findById(room.getRoomId());
        if (existingRoom == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id: " + room.getRoomId());
        }
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setAvailability(room.isAvailability());
        existingRoom.setCleaningStatus(room.getCleaningStatus());
        return roomRepository.save(existingRoom);
    }


    @Transactional
    public void deleteRoom(int roomId) {
        try {
            roomRepository.deleteById(roomId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id: " + roomId);
        }
    }

    public Room getRoomById(int roomId) {
        return roomRepository.findById(roomId);
    }
}