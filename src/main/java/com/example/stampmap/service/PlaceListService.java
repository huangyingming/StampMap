package com.example.stampmap.service;
 
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dto.Place;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class PlaceListService {
    private List<Place> places;
    @Autowired
    private PlaceDao placeDao;
    
    public Page<Place> findPaginated(Pageable pageable) {
        places = placeDao.readPlaces();
        return processPaginated(pageable, places);
    }
    
    public Page<Place> findPaginated(Pageable pageable, String query) {
        places = placeDao.readPlacesWithQuery(query);
        return processPaginated(pageable, places);
    }
    
    
    
    private Page<Place> processPaginated(Pageable pageable, List<Place> places) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Place> list;
        
        if (places.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, places.size());
            list = places.subList(startItem, toIndex);
        }
        Page<Place> placePage
          = new PageImpl<Place>(list, PageRequest.of(currentPage, pageSize), places.size());
        return placePage;
    }
}
