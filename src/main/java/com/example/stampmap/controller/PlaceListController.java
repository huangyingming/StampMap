package com.example.stampmap.controller;

import com.example.stampmap.dto.Place;
import com.example.stampmap.service.PlaceListService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

@Controller
public class PlaceListController {
    @Autowired
    private PlaceListService placeListService;
    @GetMapping("/placelist")
    public String renderPlaceList(Model model, 
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size,
        @RequestParam("query") Optional<String> query) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<Place> placePage;
        if (query.isPresent() && query.get() != ""){
            placePage = placeListService.findPaginated(PageRequest.of(currentPage - 1, pageSize), query.get());
            model.addAttribute("query", query.get());
        } else {
            placePage = placeListService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("placePage", placePage);
        int totalPages = placePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
 
        return "place-list";
    }
}
