package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomService.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomService.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-05-05
 * Description: Description: This file defines the RoomService class, which handles business logic related to room management.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cmsc495.hotelmanagementapp.reservation.Reservation;
import com.cmsc495.hotelmanagementapp.reservation.ReservationService;

import org.springframework.http.HttpStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private ReservationService reservationService;

    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        rooms.sort(Comparator.comparingInt(Room::getRoomNumber));
        return rooms;
    }

    public int countAvailableRooms() {
        List<Room> availableRooms = roomRepository.findAll().stream()
                .filter(Room::isAvailability)
                .collect(Collectors.toList());
        return availableRooms.size();
    }

    public int countCleaningPreparedRooms() {
        List<Room> cleaningPreparedRooms = roomRepository.findAll().stream()
                .filter(room -> room.getCleaningStatus().equals("Prepared"))
                .collect(Collectors.toList());
        return cleaningPreparedRooms.size();
    }
    
    public int countAvailableAndPreparedRooms() {
        List<Room> availableAndPreparedRooms = roomRepository.findAll().stream()
                .filter(room -> room.isAvailability() && room.getCleaningStatus().equals("Prepared"))
                .collect(Collectors.toList());
        return availableAndPreparedRooms.size();
    }

    @Transactional
    public void createRoom(Room room) {
        roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(int roomId, Room room) {
        Room existingRoom = roomRepository.findById(roomId);
        if (existingRoom == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id: " + roomId);
        }
        room.setRoomId(roomId);
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(int roomId) {
        List<Reservation> reservations = reservationService.getReservationsByRoomId(roomId);
        if (!reservations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete room. There are reservations associated with this room.");
        }

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

