package com.cmsc495.hotelmanagementapp.housekeeping;
/*
 * File: Housekeeping.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/housekeeping/Housekeeping.java
 * Package: com.cmsc495.hotelmanagementapp.housekeeping
 * Author: Brandon Davis
 * Created: 2024-04-11
 * Last Modified: 2024-0x-xx
 * Description: This file contains...
 * ...
 */

import java.util.List;
import com.cmsc495.hotelmanagementapp.room.Room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "housekeeping")
public class Housekeeping {

@Id// primary key of the database table
@GeneratedValue(strategy=GenerationType.IDENTITY)// housekeepingId value is generated automatically by the database
@Column(name = "HousekeepingID")
private int housekeepingId;

@OneToMany(mappedBy = "housekeeping")
    private List<Room> rooms;

public Housekeeping() {}

public int getHousekeepingId() {
return housekeepingId;
}
}