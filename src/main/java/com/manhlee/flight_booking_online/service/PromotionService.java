package com.manhlee.flight_booking_online.service;

import com.manhlee.flight_booking_online.entities.PromotionEntity;
import com.manhlee.flight_booking_online.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<PromotionEntity> getPromotions(){
        return (List<PromotionEntity>) promotionRepository.findAll();
    }

    public void save(PromotionEntity promotionEntity){
        promotionRepository.save(promotionEntity);
    }
}
