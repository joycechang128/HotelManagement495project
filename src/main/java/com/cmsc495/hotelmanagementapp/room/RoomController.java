package com.cmsc495.hotelmanagementapp.room;
/*
 * File: RoomController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/room/RoomController.java
 * Package: com.cmsc495.hotelmanagementapp.room
 * Author: Keita Alex Quirk-Arakaki
 * Created: 2024-04-11
 * Last Modified: 2024-05-05
 * Description: This file defines the RoomController class, which handles HTTP requests related to room management.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String showRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        int totalRooms = rooms.size();
        int availableRooms = roomService.countAvailableRooms();
        int cleaningPreparedRooms = roomService.countCleaningPreparedRooms();
        int availableAndPreparedRooms = roomService.countAvailableAndPreparedRooms();

        model.addAttribute("rooms", rooms);
        model.addAttribute("totalRooms", totalRooms);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("cleaningPreparedRooms", cleaningPreparedRooms);
        model.addAttribute("availableAndPreparedRooms", availableAndPreparedRooms);

        return "room";
    }

    @GetMapping("/new")
    public String showCreateRoomForm(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "new-room";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room, RedirectAttributes redirectAttributes) {
        roomService.createRoom(room);
        redirectAttributes.addFlashAttribute("successMessage", "Room created successfully");
        return "redirect:/room";
    }

    @GetMapping("/edit/{roomId}")
    public String showEditRoomForm(@PathVariable int roomId, Model model) {
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return "error";
        }
        model.addAttribute("room", room);
        return "edit-room";
    }

    @PostMapping("/edit/{roomId}")
    public String updateRoom(@PathVariable int roomId, @ModelAttribute Room room, RedirectAttributes redirectAttributes) {
        Room updatedRoom = roomService.updateRoom(roomId, room);
        if (updatedRoom == null) {
            return "error";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully");
        return "redirect:/room";
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int roomId, RedirectAttributes redirectAttributes) {
        roomService.deleteRoom(roomId);
        redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
