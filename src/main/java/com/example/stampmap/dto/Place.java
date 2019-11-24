package com.example.stampmap.dto;
import lombok.Data;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Place {
    private int placeId;
    private String placeName;
    private String description;
    private double longitude;
    private double latitude;
    private String address;
    private int userId;
    private String placeCreatedAt;
    private String placeUpdatedAt;
    private MultipartFile[] images;
    private List<String> publicIdsAndFormats;
    private String topUrl;
    
}
