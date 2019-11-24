package com.example.stampmap.controller;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import java.util.List;
import java.util.LinkedList;
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
    public String mapRender(Model model) {
        List<Place> placeList = placeDao.readPlaces();
        for (int i = 0; i < placeList.size(); i++) {
            Place place = placeList.get(i);
            int placeId = place.getPlaceId();
            String url = imageDao.readImageURLForPopUp(placeId);
            place.setTopUrl(url);
            System.out.println(url);
        }
        model.addAttribute("placeData", placeList);
        return "map";
    }
}
