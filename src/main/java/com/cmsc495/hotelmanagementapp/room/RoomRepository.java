package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomRepository.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-04-16
 * Description: This file contains...
 * 				...
 */

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room save(Room room);

    Room findById(int roomId);

    List<Room> findAll();

    void deleteById(int roomId);
}