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
}
