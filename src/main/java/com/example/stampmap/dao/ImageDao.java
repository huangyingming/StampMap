package com.example.stampmap.dao;

import com.example.stampmap.dto.Image;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ImageDao {
    void addImages(MultipartFile[] images, int placeId);
    String readImageURLForPopUp(int placeId);
    List<Map<String, Object>> readPublicIdAndFormatForDetail(int placeId);
    List<String> readPublicId(int placeId);
    List<Image> readImages();
    void deleteImage(int imageId, String publicId);
    void deleteImage(String publicId);
}
