package com.manhlee.flight_booking_online.controller;

import com.manhlee.flight_booking_online.entities.*;
import com.manhlee.flight_booking_online.enums.CommonStatus;
import com.manhlee.flight_booking_online.enums.SeatStatus;
import com.manhlee.flight_booking_online.enums.SeatType;
import com.manhlee.flight_booking_online.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private CityService cityService;

    @Autowired
    private FlightRouteService flightRouteService;

    @Autowired
    private SeatStatusService seatStatusService;

    @Autowired
    private SeatTypeService seatTypeService;

    @Autowired
    private AircraftSeatService aircraftSeatService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private OperationsService operationsService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SeatService seatService;

//    @Autowired
//    private PromotionService promotionService;

    @RequestMapping("/home")
    public String viewHome(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        model.addAttribute("message", "Hello Manager: " + username);
        return "manager/home";
    }
    @RequestMapping("/view/aircraft")
    public String viewAircraft(Model model){
        model.addAttribute("aircrafts", aircraftService.getAircrafts());
        return "manager/setup/aircraft/view-aircraft";
    }
    @RequestMapping("/aircraft/add-aircraft")
    public String addAircraft(Model model){
        model.addAttribute("aircraft", new AircraftEntity());
        model.addAttribute("action", "add");
        return "manager/setup/aircraft/edit-aircraft";
    }

    @RequestMapping(value = "/aircraft/result", method = RequestMethod.POST)
    public String resultAircraft(@ModelAttribute("aircraft") AircraftEntity aircraft){

//
//        aircraftService.save(aircraft);
        List<MultipartFile> files = aircraft.getFiles();
        List<ImageEntity> images = new ArrayList<>();
        if(files!=null && files.size()>0){
            for(MultipartFile file : files){
                ImageEntity image = new ImageEntity();
                try {
                    image.setName(file.getOriginalFilename());
                    images.add(image);

                    String fileName = file.getOriginalFilename();
                    File imageFile = new File(servletContext.getRealPath("/resources-management/image/aircraft"), fileName);
                    file.transferTo(imageFile);
                } catch (IOException e){
                    e.printStackTrace();
                }
                image.setAircraft(aircraft);
            }
            aircraft.setImages(images);
            aircraftService.save(aircraft);
        }
        return "redirect:/manager/view/aircraft";
    }

    @RequestMapping(value = "/aircraft/edit/{id}")
    public String editAircraft(Model model, @PathVariable("id") int id){

        model.addAttribute("aircraft", aircraftService.getAircraft(id));
        model.addAttribute("images", imageService.getImagesAircraft(id));
        model.addAttribute("action", "update");
        return "manager/setup/aircraft/edit-aircraft";
    }

    @RequestMapping("/aircraft/delete/{id}")
    public String updateAircraft(@PathVariable("id") int id){

        aircraftService.deleteAircraft(id);
        return "redirect:/manager/view/aircraft";
    }

    @RequestMapping("/view/airport")
    public String viewAirport(Model model){
        model.addAttribute("airports", airportService.getAirports());
        return "manager/setup/airport/view-airport";
    }

    @RequestMapping("/airport/add-airport")
    public String addAirport(Model model){
        model.addAttribute("airport", new AirportEntity());
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("action", "add");
        return "manager/setup/airport/edit-airport";
    }

    @RequestMapping(value = "/airport/result", method = RequestMethod.POST)
    public String resultAirport(@ModelAttribute("airport") AirportEntity airport){

//        System.out.println(airport.getAirportName().toString());
//        airportService.save(airport);
        List<MultipartFile> files =  airport.getFiles();
        List<ImageEntity> images = new ArrayList<>();
        if(files!=null && files.size()>0){
            for(MultipartFile file : files){
                ImageEntity image = new ImageEntity();
                try {
                    image.setName(file.getOriginalFilename());
                    images.add(image);

                    String fileName = file.getOriginalFilename();
                    File imageFile = new File(servletContext.getRealPath("/resources-management/image/airport"), fileName);
                    file.transferTo(imageFile);
                } catch (IOException e){
                    e.printStackTrace();
                }
                image.setAirport(airport);
            }
            airport.setImage(images);
            airportService.save(airport);
        }
        return "redirect:/manager/view/airport";
    }

    @RequestMapping(value = "/airport/edit/{id}")
    public String editAirport(Model model, @PathVariable("id") int id){

        model.addAttribute("airport", airportService.getAirport(id));
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("images", imageService.getImagesAirport(id));
        model.addAttribute("action", "update");
        return "manager/setup/airport/edit-airport";
    }

    @RequestMapping("/airport/delete/{id}")
    public String deleteAirport(@PathVariable("id") int id){

        airportService.deleteAirport(id);
        return "redirect:/manager/view/airport";
    }

    @RequestMapping("/flight-route/view")
    public String viewFlightRoute(Model model){
        model.addAttribute("flightRoutes", flightRouteService.getFlightRoutes());
        return "manager/setup/flight-route/view-flightRoute";
    }

    @RequestMapping(value = "/flight-route/add")
    public String addFlightRoute(Model model){
        model.addAttribute("flightRoute", new FlightRouteEntity());
        model.addAttribute("airports", airportService.getAirports());
        model.addAttribute("status", CommonStatus.values());
        model.addAttribute("action", "add");
        return "manager/setup/flight-route/edit-flight-route";
    }

    @RequestMapping(value = "/flight-route/result", method = RequestMethod.POST)
    public String resultFlightRoute(@ModelAttribute("flightRoute") FlightRouteEntity flightRoute){
        flightRouteService.save(flightRoute);
        return "redirect:/manager/flight-route/view";
    }

    @RequestMapping("/flight-route/edit/{id}")
    public String updateFlightRoute(Model model, @PathVariable("id") int id){
        model.addAttribute("flightRoute", flightRouteService.getFlightRoute(id));
        model.addAttribute("airports", airportService.getAirports());
        model.addAttribute("status", CommonStatus.values());
        model.addAttribute("action", "update");
        return "manager/setup/flight-route/edit-flight-route";
    }

    @RequestMapping("/flight-route/delete/{id}")
    public String deleteFlightRoute(@PathVariable("id") int id){
        flightRouteService.deleteFlightRoute(id);
        return "redirect:/manager/flight-route/view";
    }

    @RequestMapping("/flight/view")
    public String viewFlight(Model model){
        model.addAttribute("flights", flightService.getFlights());
        return "manager/setup/flight/view-flight";
    }

    @RequestMapping(value = "/flight/add")
    public String addFlight(Model model){
        model.addAttribute("flight", new FlightEntity());
        model.addAttribute("aircrafts", aircraftService.getAircrafts());
        model.addAttribute("flightRoutes", flightRouteService.getFlightRoutes());
        model.addAttribute("promotions");
        model.addAttribute("operations", operationsService.getOperations());
        model.addAttribute("status", CommonStatus.values());
        model.addAttribute("action", "add");
        return "manager/setup/flight/edit-flight";
    }

    @RequestMapping(value = "/flight/result", method = RequestMethod.POST)
    public String resultFlight(@ModelAttribute("flight") FlightEntity flight){
        flightService.save(flight);
        return "redirect:/manager/flight/view";
    }

    @RequestMapping("/flight/edit/{id}")
    public String updateFlight(Model model, @PathVariable("id") int id){
        model.addAttribute("flight", flightService.getFlight(id));
        model.addAttribute("aircrafts", aircraftService.getAircrafts());
        model.addAttribute("flightRoutes", flightRouteService.getFlightRoutes());
        model.addAttribute("promotions");
        model.addAttribute("operations", operationsService.getOperations());
        model.addAttribute("status", CommonStatus.values());
        model.addAttribute("action", "update");
        return "manager/setup/flight/edit-flight";
    }

    @RequestMapping("/flight/delete/{id}")
    public String deleteFlight(@PathVariable("id") int id){
        return "redirect:/manager/flight/view";
    }

    @RequestMapping("/aircraft-seat/view")
    public String viewAircraftSeat(Model model){
        model.addAttribute("flightRoutes", flightRouteService.getFlightRoutes());
        return "manager/setup/flight-route/view-flightRoute";
    }

    @RequestMapping(value = "/aircraft-seat/add")
    public String addAircraftSeat(Model model){
        model.addAttribute("aircraftSeat", new AircraftSeatEntity());
        model.addAttribute("seatTypes", SeatType.values());
        model.addAttribute("seatStatus", SeatStatus.values());
        model.addAttribute("aircrafts", aircraftService.getAircrafts());
        model.addAttribute("flights", flightService.getFlights());
        model.addAttribute("action", "add");
        return "manager/setup/aircraft/set-seat";
    }

    @RequestMapping(value = "/aircraft-seat/result", method = RequestMethod.POST)
    public String resultAircraftSeat(@ModelAttribute("aircraftSeat") AircraftSeatEntity aircraftSeat){

        SeatStatusEntity seatStatus = seatStatusService.getSeatStatus(aircraftSeat.getSeatStatus().getStatus().name()).get(0);
        aircraftSeat.setSeatStatus(seatStatus);

        SeatTypeEntity seatType = seatTypeService.getSeatType(aircraftSeat.getSeatType().getSeatType().name()).get(0);
        aircraftSeat.setSeatType(seatType);
        aircraftSeatService.save(aircraftSeat);
        return "redirect:/manager/flight-route/view";
    }

    @RequestMapping("/aircraft-seat/edit/{id}")
    public String updateAircraftSeat(Model model, @PathVariable("id") int id){
        model.addAttribute("flightRoute", flightRouteService.getFlightRoute(id));
        model.addAttribute("airports", airportService.getAirports());
        model.addAttribute("action", "update");
        return "manager/setup/flight-route/edit-flight-route";
    }

    @RequestMapping("/aircraft-seat/delete/{id}")
    public String deleteAircraftSeat(@PathVariable("id") int id){
        flightRouteService.deleteFlightRoute(id);
        return "redirect:/manager/flight-route/view";
    }

//    @RequestMapping("/promotion/view")
//    public String viewPromotion(Model model){
//        model.addAttribute("promotions", promotionService.getPromotions());
//        return "manager/manage/promotion/view-promotion";
//    }
//    @RequestMapping("/promotion/add-promotion")
//    public String addPromotion(Model model){
//        model.addAttribute("promotion", new PromotionEntity());
//        model.addAttribute("flights", flightService.getFlights());
//        model.addAttribute("action", "add");
//        return "manager/manage/promotion/edit-promotion";
//    }
//
//    @RequestMapping(value = "/promotion/result", method = RequestMethod.POST)
//    public String resultPromotion(@ModelAttribute("promotion") PromotionEntity promotion){
//
////
////        aircraftService.save(aircraft);
//        List<MultipartFile> files = promotion.getFiles();
//        List<ImageEntity> images = new ArrayList<>();
//        if(files!=null && files.size()>0){
//            for(MultipartFile file : files){
//                ImageEntity image = new ImageEntity();
//                try {
//                    image.setName(file.getOriginalFilename());
//                    images.add(image);
//
//                    String fileName = file.getOriginalFilename();
//                    File imageFile = new File(servletContext.getRealPath("/resources-management/image/promotion"), fileName);
//                    file.transferTo(imageFile);
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//                image.setPromotion(promotion);
//            }
//            promotion.setImages(images);
//            promotionService.save(promotion);
//        }
//        return "redirect:/manager/promotion/view";
//    }
//
//    @RequestMapping(value = "/promotion/edit/{id}")
//    public String editPromotion(Model model, @PathVariable("id") int id){
//
//        model.addAttribute("aircraft", aircraftService.getAircraft(id));
//        model.addAttribute("images", imageService.getImagesAircraft(id));
//        model.addAttribute("action", "update");
//        return "manager/manage/promotion/edit-promotion";
//    }
}
