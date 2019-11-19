package com.example.stampmap.dao;
import org.springframework.web.multipart.MultipartFile;
public interface ImageDao {
    void addImages(MultipartFile[] images, int placeId);
}
