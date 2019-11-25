package com.example.stampmap.dto;
import lombok.Data;

@Data
public class Comment {
    private int commentId;
    private int placeId;
    private String comment;
    private int userId;
    private String commentCreatedAt;
    private String commentUpdatedAt;
}

