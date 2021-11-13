package com.manhlee.flight_booking_online.repository;

import com.manhlee.flight_booking_online.entities.PromotionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends CrudRepository<PromotionEntity, Integer> {
}
