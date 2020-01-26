package com.example.stampmap.dao;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.stampmap.dto.Image;
import com.example.stampmap.Utility;
import java.io.IOException;
import java.util.ArrayList;
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
        String url = "https://res.cloudinary.com/foehammer/image/upload/w_200,h_200,c_limit/"+publicId+"."+format;
        return url;
    }
    
    public List<Map<String, Object>> readPublicIdAndFormatForDetail(int placeId) {
        String sql = "SELECT public_id, format FROM images WHERE place_id=?";
        RowMapper rowMapper = new ColumnMapRowMapper();
        List<Map<String, Object>> result = jdbcTemplate.query(sql, rowMapper, placeId);
        return result;
    }
    
    public List<Image> readImages() {
        String sql = "SELECT * FROM images";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        List<Image> imageList = new ArrayList();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> row = resultList.get(i);
            Image image = makeImageFromRow(row);
            imageList.add(image);
        }
        return imageList;
    }
    
    private Image makeImageFromRow(Map<String, Object> row) {
        Image image = new Image();
        image.setImageId(Integer.valueOf(row.get("image_id").toString()));
        image.setPlaceId(Integer.valueOf(row.get("place_id").toString()));
        image.setPublicId(row.get("public_id").toString());
        image.setFormat(row.get("format").toString());
        image.setUserId(Integer.valueOf(row.get("user_id").toString()));
        image.setImageCreatedAt(row.get("image_created_at").toString());
        return image;
    }
    
    public void deleteImage(int imageId, String publicId) {
        String sql = "DELETE FROM images WHERE image_id=?";
        System.out.println(publicId);
        jdbcTemplate.update(sql, imageId);
        try {
            Map map = cloudinary.uploader().destroy("stamps/"+ publicId, ObjectUtils.emptyMap());
        } catch(IOException e) {
            System.out.println("IOException at deleteImage.");
        }
    }
    
    public List<String> readPublicId(int placeId) {
        String sql = "SELECT public_id FROM images WHERE place_id = ?";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, placeId);
        List<String> publicIdList = new ArrayList();
        for (int i = 0; i < resultList.size(); i++) {
            String publicId = resultList.get(i).get("public_id").toString();
            publicIdList.add(publicId);
        }
        return publicIdList;
    }
    
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy("stamps/" + publicId, ObjectUtils.emptyMap());
        } catch(IOException e) {
            System.out.println("IOException at deleteImages()");
        }
        String sql = "DELETE FROM images WHERE public_id = ?";
        jdbcTemplate.update(sql, publicId);
;    }
}
