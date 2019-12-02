package com.example.stampmap.dao;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.stampmap.Utility;
import java.io.IOException; 
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class ImageDaoImpl implements ImageDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static Cloudinary cloudinary = new Cloudinary(
        ObjectUtils.asMap(
        "cloud_name", "foehammer",
        "api_key", System.getenv("CLOUDINARY_APIKEY"),
        "api_secret", System.getenv("CLOUDINARY_SECRET")
        ));
    public void addImages(MultipartFile[] images, int placeId) {
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
    
    public String readImageURLForPopUp(int placeId) {
        String sql = "SELECT public_id, format FROM images WHERE place_id=? LIMIT 1";
        RowMapper rowMapper = new ColumnMapRowMapper();
        List<Map<String, Object>> result = jdbcTemplate.query(sql, rowMapper, placeId);
        if (result.isEmpty()) return null;
        String publicId = result.get(0).get("public_id").toString();
        String format = result.get(0).get("format").toString();
        String url = "https://res.cloudinary.com/foehammer/image/upload/w_250,h_250,c_limit/"+publicId+"."+format;
        return url;
    }
    
    public List<Map<String, Object>> readPublicIdForDetail(int placeId) {
        String sql = "SELECT public_id, format FROM images WHERE place_id=?";
        RowMapper rowMapper = new ColumnMapRowMapper();
        List<Map<String, Object>> result = jdbcTemplate.query(sql, rowMapper, placeId);
        return result;
    }
}
