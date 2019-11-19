package com.example.stampmap.controller;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dto.Place;
import com.example.stampmap.dao.PlaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class MapController {
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private ImageDao imageDao;
    
    @GetMapping("/map")
    public String registrationForm(Model model) {
        String placeDataJSON = placeDao.readPlaceDataAll();
        model.addAttribute("placeDataJSON", placeDataJSON);
        return "map";
    }
}
