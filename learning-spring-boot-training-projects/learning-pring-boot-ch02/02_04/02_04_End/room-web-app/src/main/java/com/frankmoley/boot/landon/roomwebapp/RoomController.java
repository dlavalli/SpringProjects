package com.frankmoley.boot.landon.roomwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private RoomServices roomServices;

    // Proper autowiring should be done on a constructor, especially for a required attribute,
    //  Now because it's autowired, Spring will take care of this when we start. Now all we need
    //  to do is actually fix in our GetMapping method how we get the rooms for our model,
    //  and we're gonna do it by calling this.roomServices.getAllRooms.
    @Autowired
    public RoomController(RoomServices roomServices){
        super();
        this.roomServices = roomServices;
    }

    @GetMapping
    public String getAllRooms(Model model){
        model.addAttribute("rooms", this.roomServices.getAllRooms());
        return "rooms";
    }
}
