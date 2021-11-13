package com.manhlee.flight_booking_online.controller;

import com.manhlee.flight_booking_online.entities.ImageEntity;
import com.manhlee.flight_booking_online.entities.PromotionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.manhlee.flight_booking_online.service.FlightService;
import com.manhlee.flight_booking_online.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager/promotion")
public class PromotionController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private FlightService flightService;

    @RequestMapping("/promotion/view")
    public String viewPromotion(Model model){
        model.addAttribute("promotions", promotionService.getPromotions());
        return "manager/manage/promotion/view-promotion";
    }
    @RequestMapping("/promotion/add-promotion")
    public String addPromotion(Model model){
        model.addAttribute("promotion", new PromotionEntity());
        model.addAttribute("flights", flightService.getFlights());
        model.addAttribute("action", "add");
        return "manager/manage/promotion/edit-promotion";
    }

    @RequestMapping(value = "/promotion/result", method = RequestMethod.POST)
    public String resultPromotion(@ModelAttribute("promotion") PromotionEntity promotion){

//
//        aircraftService.save(aircraft);
        List<MultipartFile> files = promotion.getFiles();
        List<ImageEntity> images = new ArrayList<>();
        if(files!=null && files.size()>0){
            for(MultipartFile file : files){
                ImageEntity image = new ImageEntity();
                try {
                    image.setName(file.getOriginalFilename());
                    images.add(image);

                    String fileName = file.getOriginalFilename();
                    File imageFile = new File(servletContext.getRealPath("/resources-management/image/promotion"), fileName);
                    file.transferTo(imageFile);
                } catch (IOException e){
                    e.printStackTrace();
                }
                image.setPromotion(promotion);
            }
            promotion.setImages(images);
            promotionService.save(promotion);
        }
        return "redirect:/manager/promotion/view";
    }

//    @RequestMapping(value = "/promotion/edit/{id}")
//    public String editPromotion(Model model, @PathVariable("id") int id){
//
//        model.addAttribute("aircraft", aircraftService.getAircraft(id));
//        model.addAttribute("images", imageService.getImagesAircraft(id));
//        model.addAttribute("action", "update");
//        return "manager/manage/promotion/edit-promotion";
//    }
}
