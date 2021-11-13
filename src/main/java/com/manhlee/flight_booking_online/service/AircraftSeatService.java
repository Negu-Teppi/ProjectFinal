package com.manhlee.flight_booking_online.service;

import com.manhlee.flight_booking_online.entities.AircraftSeatEntity;
import com.manhlee.flight_booking_online.repository.AircraftSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AircraftSeatService {

    @Autowired
    private AircraftSeatRepository aircraftSeatRepository;

    public void save(AircraftSeatEntity aircraftSeatEntity){
        aircraftSeatRepository.save(aircraftSeatEntity);
    }
}
