package com.cmsc495.hotelmanagementapp;

/*
* File: roomControllerTests.java
* Path: src/test/java/com/cmsc495/hotelmanagementapp/roomControllerTests.java
* Package: com.cmsc495.hotelmanagementapp
* Author: Brandon Davis
* Created: 2024-05-05
* Last Modified: 2024-05-08
* Description: This file contains the JUnit Test for the Room Controller.
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import com.cmsc495.hotelmanagementapp.room.Room;
import com.cmsc495.hotelmanagementapp.room.RoomController;
import com.cmsc495.hotelmanagementapp.room.RoomService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    // Test displaying all rooms
    @Test
    void testShowRooms() throws Exception {
        when(roomService.getAllRooms()).thenReturn(Arrays.asList(new Room()));
        when(roomService.countAvailableRooms()).thenReturn(10);
        when(roomService.countCleaningPreparedRooms()).thenReturn(5);
        when(roomService.countAvailableAndPreparedRooms()).thenReturn(3);

        mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(view().name("room"))
                .andExpect(model().attributeExists("rooms", "totalRooms", "availableRooms", "cleaningPreparedRooms", "availableAndPreparedRooms"));
    }

    // Test displaying the form to create a new room
    @Test
    void testShowCreateRoomForm() throws Exception {
        mockMvc.perform(get("/room/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new-room"))
                .andExpect(model().attributeExists("room"));
    }

    // Test saving a new room
    @Test
    void testSaveRoom() throws Exception {
        mockMvc.perform(post("/room/save")
                .param("roomNumber", "101")
                .param("roomFloor", "1")
                .param("roomType", "Deluxe")
                .param("availability", "true")
                .param("cleaningStatus", "Clean"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/room"));
    }

    // Test displaying the form to edit a room
    @Test
    void testShowEditRoomForm() throws Exception {
        when(roomService.getRoomById(1)).thenReturn(new Room());

        mockMvc.perform(get("/room/edit/{roomId}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-room"))
                .andExpect(model().attributeExists("room"));
    }

    // Test updating a room
    @Test
    void testUpdateRoom() throws Exception {
        mockMvc.perform(post("/room/edit/{roomId}", 1)
                .param("roomNumber", "102")
                .param("roomFloor", "1")
                .param("roomType", "Suite")
                .param("availability", "true")
                .param("cleaningStatus", "Dirty"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/room"));
    }

    // Test deleting a room
    @Test
    void testDeleteRoom() throws Exception {
        mockMvc.perform(delete("/room/{roomId}", 1))
                .andExpect(status().isNoContent());
    }
}