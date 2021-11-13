package com.manhlee.flight_booking_online.repository;

import com.manhlee.flight_booking_online.entities.FlightEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<FlightEntity, Integer> {

    List<FlightEntity> findByAircraft_Id(int id);
}
