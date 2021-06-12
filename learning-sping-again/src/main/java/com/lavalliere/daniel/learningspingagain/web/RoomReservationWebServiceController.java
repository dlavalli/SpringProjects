package com.lavalliere.daniel.learningspingagain.web;

import java.util.Date;
import java.util.List;

import com.lavalliere.daniel.learningspingagain.business.domain.RoomReservation;
import com.lavalliere.daniel.learningspingagain.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservations")
public class RoomReservationWebServiceController {
    private final ReservationService reservationService;

    @Autowired
    public RoomReservationWebServiceController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //  Now in this case we don't pass a model and that's really important here,
    //  because there is no model. We're not doing a webpage. We're doing web services
    @GetMapping
    public List<RoomReservation> getRoomReservations(@RequestParam(name="date", required = false)String dateString){
        Date date = DateUtils.createDateFromDateString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }
}
