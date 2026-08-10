package com.security.code.controllers;

import com.security.code.entities.Room;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @PostMapping("add")
    //This annotation reduces the boilerplate code of Requestmatcher and extra-coding
    //configuration in SecurityConfig.java
   // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('ROOM_ADD')")
    public String addRoom(){
        return "Room Added";
    }

    @GetMapping("{id}")
   // @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    public String getRoomById(@PathVariable Long id){
        return "Room fetched for id "+id;
    }

    @GetMapping("user/{id}")
   // @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @PostAuthorize("returnObject.assignedTo == authentication.name")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    public Room getRoomDetailsById(@PathVariable Long id){
        return new Room(id,"Vishwajit");
    }

    @GetMapping("all")
    //@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PreAuthorize("hasAuthority('ROOM_VIEW_ALL')")
    public String getRooms(){
        return "All Rooms";
    }
}
