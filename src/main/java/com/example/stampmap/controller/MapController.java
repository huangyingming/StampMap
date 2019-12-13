package com.example.stampmap.controller;
import com.example.stampmap.Utility;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MapController {
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private ImageDao imageDao;
    
    @GetMapping("/map")
    public String mapRender(Model model) {
        List<Place> placeList = placeDao.readPlaces();
        this.setPlaceList(model, placeList);
        return "map";
    }
    
    @GetMapping("/map/visited")
    public String visitedRender(Model model) {
        int userId = Utility.getCurrentUserId();
        if (userId == -1) {
            return "redirect:/index";
        }
        List<Place> placeList = placeDao.readPlacesForVisited(userId);
        setPlaceList(model, placeList);
        return "map";
    }
    
    private  void setPlaceList(Model model, List<Place> placeList) {
        for (int i = 0; i < placeList.size(); i++) {
            Place place = placeList.get(i);
            int placeId = place.getPlaceId();
            String url = imageDao.readImageURLForPopUp(placeId);
            place.setTopUrl(url);
        }
        model.addAttribute("placeList", placeList);
    }
}
