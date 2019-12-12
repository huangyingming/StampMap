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
        model.addAttribute("place", place);
        model.addAttribute("actionUrl", "/edit");
        model.addAttribute("fillActionUrl", "/edit/fill");
        model.addAttribute("imagesClass", "hidden");
        return "registration";
    }
    
    @PostMapping("/edit")
    public String editSubmit(@ModelAttribute Place place) {
        placeDao.updatePlace(place);
        return "redirect:/detail/" + Integer.toString(place.getPlaceId());
    }
    
    @PostMapping("/edit/fill")
    public String fillAddressAndLatLng(@ModelAttribute Place place, Model model) {
        String placeName = place.getPlaceName() ;
        JSONObject json = registrationService.readJsonFromPlaceName(placeName);
        place.setImages(null);
        model.addAttribute("json", json.toString());
        model.addAttribute("place", place);
        model.addAttribute("actionUrl", "/edit");
        model.addAttribute("fillActionUrl", "/edit/fill");
        model.addAttribute("imagesClass", "hidden");
        return "registration";
    }
    
    
    
}