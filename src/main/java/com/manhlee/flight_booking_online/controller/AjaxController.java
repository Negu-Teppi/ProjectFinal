package com.manhlee.flight_booking_online.controller;

import com.manhlee.flight_booking_online.entities.SeatEntity;
import com.manhlee.flight_booking_online.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("api/")
public class AjaxController {

    @Autowired
    private SeatService seatService;

    @RequestMapping("/getSeats")
    public String getSeats(@RequestParam("aircraftId") int id, Model model){
        List<SeatEntity> getSeats =  seatService.getSeats(id);
        Map<Integer, String> seats = new HashMap<>();
        for (SeatEntity seat: getSeats) {
            seats.put(seat.getId(), seat.getSeatNumber());
        }

        model.addAttribute("seats", seats);
        return "";
    }
}
