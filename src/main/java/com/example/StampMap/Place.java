/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StampMap;
import java.util.ArrayList;
import lombok.Data;
import java.awt.Image;
/**
 *
 * @author aaa
 */

@Data
public class Place {
    private int placeId;
    private String placeName;
    private String description;
    private double longitude;
    private double latitude;
    private String address;
    private ArrayList<Image> images;
    /*
    public Place(int placeId, String placeName, String description, 
            double longitude, double latitude, String address, 
            ArrayList<Integer> images) {
        this.placeId = placeId;
        this.placeName = placeName;
        this. description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.images = images;
    }
    */
}
