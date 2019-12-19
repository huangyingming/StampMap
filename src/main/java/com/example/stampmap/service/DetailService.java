package com.example.stampmap.service;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.Place;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Service
public class DetailService {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private PlaceDao placeDao;
    public Place populatePlace(int placeId) {
        List<Map<String, Object>> publicIdList = imageDao.readPublicIdForDetail(placeId);
        Place place = placeDao.readPlace(placeId);
        List<String> publicIds = new ArrayList<String>();
        for (int i = 0; i < publicIdList.size(); i++) {
            Map<String, Object> row = publicIdList.get(i);
            String publicId = row.get("public_id").toString();
            String format = row.get("format").toString();
            publicIds.add(publicId + "." + format);
        }
        place.setPublicIdsAndFormats(publicIds);
        return place;
    }
    
    public void handleRedirectAttributes(Model model) {
        Object imageLoggedIn = model.getAttribute("imageLoggedIn");
        Object commentLoggedIn = model.getAttribute("commentLoggedIn");
        Object editLoggedIn = model.getAttribute("editLoggedIn");
        if (imageLoggedIn == null || imageLoggedIn.toString().equals("")) {
            model.addAttribute("imageLoggedIn", "hidden");
        } 
        if (commentLoggedIn == null || commentLoggedIn.toString().equals("")) {
            model.addAttribute("commentLoggedIn", "hidden");
        } 
        if (editLoggedIn == null || editLoggedIn.toString().equals("")) {
            model.addAttribute("editLoggedIn", "hidden");
        } 
    }
}
