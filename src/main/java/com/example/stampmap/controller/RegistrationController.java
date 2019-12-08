package com.example.stampmap.controller;
import com.example.stampmap.Utility;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import com.example.stampmap.service.RegistrationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class RegistrationController {
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private RegistrationService registrationService;
    
    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("place", new Place());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute Place place, Model model) {
        if (!Utility.isLoggedIn()) return "redirect:/account/index";
        int lastInsertedId = placeDao.addPlace(place);
        MultipartFile[] images = place.getImages();
        imageDao.addImages(images, lastInsertedId);
        model.addAttribute("insertedId", lastInsertedId);
        return "index";
    }
    
    @PostMapping("/registration/fill")
    public String fillAddressAndLatLng(@ModelAttribute Place place, Model model) {
        String placeName = place.getPlaceName();
        System.out.println(placeName);
        JSONObject json = registrationService.readJsonFromPlaceName(placeName);
        System.out.println(json.toString());
        model.addAttribute("json", json.toString());
        return "registration";
    }
}
