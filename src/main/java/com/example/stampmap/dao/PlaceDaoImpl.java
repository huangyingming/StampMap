package com.example.stampmap.dao;

import com.example.stampmap.dto.Place;
import com.example.stampmap.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class PlaceDaoImpl implements PlaceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
        
	
    public int addPlace(Place place) {
        String currentDatetime = Utility.getCurrentDatetime();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO places "
             + "(place_id, place_name, description, latitude, longitude, address, "
             + "place_created_at, place_updated_at, contains_permanent, user_id)"
             + "VALUES(?, ?, ?, ? , ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = 
                        connection.prepareStatement(sql, new String[] {"place_id"});
                    ps.setObject(1, null);
                    ps.setString(2, place.getPlaceName());
                    ps.setString(3, place.getDescription());
                    ps.setDouble(4, place.getLatitude());
                    ps.setDouble(5, place.getLongitude());
                    ps.setString(6, place.getAddress());
                    ps.setString(7, currentDatetime);
                    ps.setString(8, null);
                    ps.setBoolean(9, true);
                    ps.setInt(10, 1);
                    return ps;
                }
            }, keyHolder
        );
        int lastInsertedId = keyHolder.getKey().intValue();
        return lastInsertedId;
    }
    
    public String readPlaceDataAll() {
        String sql = "SELECT * FROM places";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> row = resultList.get(i);
            JSONObject item = new JSONObject();
            item.put("placeId", row.get("place_id").toString());
            item.put("place_name", row.get("place_name").toString());
            item.put("latitude", Double.valueOf(row.get("latitude").toString()));
            item.put("longitude", Double.valueOf(row.get("longitude").toString()));
            item.put("description", row.get("description").toString());
            item.put("userId", Integer.valueOf(row.get("user_id").toString()));
            jsonArray.put(item);
         }
        JSONObject placeDataJSON = new JSONObject();
        placeDataJSON.put("placeData", jsonArray);
        return placeDataJSON.toString();
    }
           
}