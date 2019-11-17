package com.example.stampmap.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Place {
    private int placeId;
    private String placeName;
    private String description;
    private double longitude;
    private double latitude;
    private String address;
    private MultipartFile[] images;
    /*
    public Place(int placeId, String placeName, String description, 
            double longitude, double latitude, String address, 
            ArrayList<Image> images) {
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
