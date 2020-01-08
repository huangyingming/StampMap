package com.example.stampmap.controller;

import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import com.example.stampmap.service.UploadService;
import com.example.stampmap.Utility;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;


@Controller
public class EditController {
    @Autowired
    private PlaceDao placeDao;
    @Autowired UploadService uploadService;
    @GetMapping("/edit") 
    public String editForm(Place place, Model model, RedirectAttributes redirectAttrs) {
        if (!Utility.isLoggedIn()) {
            redirectAttrs.addFlashAttribute("editLoggedIn", "notLoggedIn");
            return "redirect:/detail/" + Integer.toString(place.getPlaceId());
        }
        model.addAttribute("place", place);
        model.addAttribute("actionUrl", "/edit");
        model.addAttribute("fillActionUrl", "/edit/fill");
        model.addAttribute("imagesClass", "hidden");
        return "upload";
    }
    
    @PostMapping("/edit")
    public String editSubmit(@Validated @ModelAttribute Place place, BindingResult result, Model model) {
        if (result.hasErrors()) {
            place.setImages(null);
            model.addAttribute("place", place);
            model.addAttribute("fillActionUrl", "/edit/fill");
            model.addAttribute("imagesClass", "hidden");
            return "upload";
        }
        String currentDatetime = Utility.getCurrentDatetime();
        place.setPlaceUpdatedAt(currentDatetime);
        placeDao.updatePlace(place);
        return "redirect:/detail/" + Integer.toString(place.getPlaceId());
    }
    
    @PostMapping("/edit/fill")
    public String fillAddressAndLatLng(@ModelAttribute Place place, Model model) {
        String placeName = place.getPlaceName() ;
        JSONObject json = uploadService.readJsonFromPlaceName(placeName);
        place.setImages(null);
        model.addAttribute("json", json.toString());
        model.addAttribute("place", place);
        model.addAttribute("actionUrl", "/edit");
        model.addAttribute("fillActionUrl", "/edit/fill");
        model.addAttribute("imagesClass", "hidden");
        return "upload";
    }
}