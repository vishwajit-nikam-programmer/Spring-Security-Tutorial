package com.security.code.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @PostMapping("add")
    public String addRoom(){
        return "Room Added";
    }

    @GetMapping("{id}")
    public String getRoomById(@PathVariable Long id){
        return "Room fetched for id"+id;
    }

    @GetMapping("all")
    public String getRooms(){
        return "All Rooms";
    }
}
