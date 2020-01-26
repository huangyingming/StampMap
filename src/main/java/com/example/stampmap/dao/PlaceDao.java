package com.example.stampmap.dao;

import com.example.stampmap.dto.Place;
import java.util.List;
import org.json.JSONArray;

public interface PlaceDao {
	int addPlace(Place place);
        JSONArray readPlacesJSON();
        Place readPlace(int placeId);
        List<Place> readPlaces();
        void updatePlace(Place place);
        List<Place> readPlacesForVisited(int userId);
        void deletePlace(int placeId);
        List<Place> readPlacesWithQuery(String query);
        
}
