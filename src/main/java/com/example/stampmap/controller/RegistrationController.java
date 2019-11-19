package com.example.stampmap.controller;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dto.Place;
import com.example.stampmap.dao.PlaceDao;
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
    
    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("place", new Place());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute Place place, Model model) {
        int lastInsertedId = placeDao.addPlace(place);
        MultipartFile[] images = place.getImages();
        imageDao.addImages(images, lastInsertedId);
        model.addAttribute("insertedId", lastInsertedId);
        return "index";
    }
}
