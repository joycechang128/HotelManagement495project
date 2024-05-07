package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomRepository.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomRepository.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-05-03
 * Description: This file defines the RoomRepository interface, which extends JpaRepository for Room entities. 
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findById(int roomId);

    List<Room> findAll();

    Room save(Room room);

    void deleteById(int roomId);
    
    Room findByRoomNumber(int roomNumber);
}
