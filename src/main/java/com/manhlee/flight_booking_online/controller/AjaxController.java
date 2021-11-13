package com.manhlee.flight_booking_online.controller;

import com.manhlee.flight_booking_online.entities.SeatEntity;
import com.manhlee.flight_booking_online.entities.AirportEntity;
import com.manhlee.flight_booking_online.entities.FlightEntity;
import com.manhlee.flight_booking_online.entities.FlightRouteEntity;
import com.manhlee.flight_booking_online.service.SeatService;
import com.manhlee.flight_booking_online.service.AirportService;
import com.manhlee.flight_booking_online.service.FlightService;
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

    @Autowired
    private AirportService airportService;

    @Autowired
    private FlightService flightService;

    @RequestMapping("/getSeats")
    public String getSeats(@RequestParam("aircraftId") int id, Model model){
        List<SeatEntity> getSeats =  seatService.getSeats(id);
        Map<Integer, String> seats = new HashMap<>();
        for (SeatEntity seat: getSeats) {
            seats.put(seat.getId(), seat.getSeatNumber());
        }
        model.addAttribute("seats", seats);
        return "manager/ajax/load-seat";
    }

    @RequestMapping("/getDestination")
    public String getDestination(@RequestParam("departureId") int id, Model model){
        List<AirportEntity> getDestinations = airportService.getDestination(id);
        Map<Integer, String> destinations = new HashMap<>();
        for (AirportEntity airport: getDestinations){
            destinations.put(airport.getId(), airport.getAirportName());
        }
        model.addAttribute("destinations", destinations);
        return "manager/ajax/load-destination";
    }

    @RequestMapping("/getFlights")
    public String getFlights(@RequestParam("aircraftId") int id, Model model){
        List<FlightEntity> getFlights =  flightService.getFlightByAircraftId(id);
        Map<Integer, FlightRouteEntity> flights = new HashMap<>();
        for (FlightEntity flight: getFlights){
            flights.put(flight.getId(), flight.getFlightRoute());
        }
        model.addAttribute("flights", flights);
        return "manager/ajax/load-flight";
    }
}
