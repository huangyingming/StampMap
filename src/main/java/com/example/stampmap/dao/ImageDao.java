package com.example.stampmap.dao;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ImageDao {
    void addImages(MultipartFile[] images, int placeId);
    String readImageURLForPopUp(int placeId);
    List<Map<String, Object>> readPublicIdForDetail(int placeId);
}
