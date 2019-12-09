package com.example.stampmap.controller;

import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import com.example.stampmap.service.RegistrationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.ui.Model;


@Controller
public class EditController {
    @Autowired
    private PlaceDao placeDao;
    @Autowired RegistrationService registrationService;
    @GetMapping("/edit") 
    public String editForm(Place place, Model model
            ) {
        System.out.println(place.getPlaceName());
        model.addAttribute("place", place);
        return "edit";
    }
    
    @PutMapping("/edit/{placeId}")
    public String editSubmit(@ModelAttribute Place place, @PathVariable int placeId) {
        placeDao.updatePlace(place);
        return "redirect:/detail" + Integer.toString(placeId);
    }
    
    @PutMapping("/edit/fill")
    public String fillAddressAndLatLng(@ModelAttribute Place place, Model model) {
        String placeName = place.getPlaceName() ;
        JSONObject json = registrationService.readJsonFromPlaceName(placeName);
        model.addAttribute("json", json.toString());
        return "edit";
    }
    
    
    
}