package com.example.stampmap.controller;

import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import com.example.stampmap.service.UploadService;
import com.example.stampmap.Utility;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class UploadController {
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UploadService uploadService;
    
    @GetMapping("/upload")
    public String uploadForm(Model model) {
        if (!Utility.isLoggedIn()) return "redirect:/index";
        model.addAttribute("place", new Place());
        model.addAttribute("actionUrl", "/upload");
        model.addAttribute("fillActionUrl", "/upload/fill");
        return "upload";
    }

    @PostMapping("/upload")
    public String ulploadSubmit(@Validated @ModelAttribute Place place,  BindingResult result, Model model) {
        //if (!Utility.isLoggedIn()) return "redirect:/account/index";
        if (result.hasErrors()) {
            place.setImages(null);
            model.addAttribute("place", place);
            model.addAttribute("actionUrl", "/upload");
            model.addAttribute("fillActionUrl", "/upload/fill");
            return "upload";
        }
        place.setUserId(Utility.getCurrentUserId());
        int lastInsertedId = placeDao.addPlace(place);
        MultipartFile[] images = place.getImages();
        imageDao.addImages(images, lastInsertedId);
        model.addAttribute("insertedId", lastInsertedId);
        return "redirect:/detail/" + Integer.toString(lastInsertedId);
    }
    
    @PostMapping("/upload/fill")
    public String fillAddressAndLatLng(@ModelAttribute Place place, Model model) {
        String placeName = place.getPlaceName();
        JSONObject json = uploadService.readJsonFromPlaceName(placeName);
        place.setImages(null);
        model.addAttribute("json", json.toString());
        model.addAttribute("place", place);
        model.addAttribute("actionUrl", "/upload");
        model.addAttribute("fillActionUrl", "/upload/fill");
        return "upload";
    }
}
