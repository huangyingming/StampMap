package com.example.stampmap.dao;

import com.example.stampmap.dto.Place;

public interface PlaceDao {
	int addPlace(Place place);
        String readPlaceDataAll();
}