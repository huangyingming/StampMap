package com.example.stampmap.dao;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.stampmap.Utility;
import java.io.IOException; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
@Repository
public class ImageDaoImpl implements ImageDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static Cloudinary cloudinary = new Cloudinary(
        ObjectUtils.asMap(
        "cloud_name", "foehammer",
        "api_key", "",
        "api_secret", ""
        ));
    public void insertImages(MultipartFile[] images, int placeId) {
        String currentDatetime = Utility.getCurrentDatetime();
        try {
            for (MultipartFile image : images) {
                Map<String, Object> resultMap = 
                    cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap(
                        "folder", "stamps"
                        ));
                String publicId = resultMap.get("public_id").toString();
                String format = resultMap.get("format").toString();
                String sql = "INSERT INTO images(image_id, place_id, public_id, format, user_id, image_created_at)"
                    + "VALUES(?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sql, null, placeId, publicId, format, 1, currentDatetime);
                
            }
        } catch(IOException e) {
            System.out.println("IOException\n");
        }
        
        
    }
}
