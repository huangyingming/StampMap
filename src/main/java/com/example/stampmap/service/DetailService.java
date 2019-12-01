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
}
