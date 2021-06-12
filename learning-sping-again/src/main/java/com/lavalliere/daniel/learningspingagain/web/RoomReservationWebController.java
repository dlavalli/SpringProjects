package com.lavalliere.daniel.learningspingagain.web;

import java.util.Date;
import java.util.List;

import com.lavalliere.daniel.learningspingagain.business.domain.RoomReservation;
import com.lavalliere.daniel.learningspingagain.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservations")
public class RoomReservationWebController {
    private final ReservationService reservationService;

    @Autowired
    public RoomReservationWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Any get method will be responded to by this
    @GetMapping
    public String getReservations(@RequestParam(value="date", required = false) String dateString, Model model) {
        Date date = DateUtils.createDateFromDateString(dateString);
        List<RoomReservation> roomReservations = this.reservationService.getRoomReservationForDate(date);
        model.addAttribute("roomreservations", roomReservations);

        // What this is going to do is tell TimeLeaf to find a template named reservations, use that template,
        // pass this model that came into the method, but pass it too the reservations template and merge with the TimeLeaf engine
        return "reservations";
    }
}
