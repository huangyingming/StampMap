package com.example.stampmap.dto;

import lombok.Data;

@Data
public class Image {
    private int imageId;
    private int placeId;
    private String publicId;
    private String format;
    private int userId;
    private String imageCreatedAt;
}
