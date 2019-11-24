package com.example.stampmap.controller;

import com.example.stampmap.dto.Place;
import com.example.stampmap.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@RequestMapping("/detail")
@Controller
public class DetailController {
    @Autowired
    private DetailService detailService;
    
    @GetMapping("/{placeId}") 
    public String detailRender(@PathVariable int placeId, Model model) {
        Place place = detailService.populatePlace(placeId);
        model.addAttribute("place", place);
        return "detail";
    } 
}
