package com.example.stampmap.dao;

import com.example.stampmap.dto.Place;
import com.example.stampmap.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    ps.setInt(10, place.getUserId());
                    return ps;
                }
            }, keyHolder
        );
        int lastInsertedId = keyHolder.getKey().intValue();
        return lastInsertedId;
    }
    
    public Place readPlace(int placeId) {
        String sql = "SELECT * FROM places WHERE place_id=? LIMIT 1";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, placeId);
        return makePlaceFromRow(resultList.get(0));
    }
    
    public List<Place> readPlaces() {
        List<Map<String, Object>> resultList = selectPlaces();
        List<Place> placeList = new ArrayList<Place>();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> row = resultList.get(i);
            Place place = makePlaceFromRow(row);
            placeList.add(place);
        }
        return placeList;
    }
    private Place makePlaceFromRow(Map<String, Object> row) {
        Place place = new Place();
        place.setPlaceId(Integer.valueOf(row.get("place_id").toString()));
        place.setPlaceName(row.get("place_name").toString());
        place.setDescription(row.get("description").toString());
        place.setLatitude(Double.valueOf(row.get("latitude").toString()));
        place.setLongitude(Double.valueOf(row.get("longitude").toString()));
        place.setAddress(row.get("address").toString());
        place.setUserId(Integer.valueOf(row.get("user_id").toString()));
        place.setPlaceCreatedAt(row.get("place_created_at").toString());
        if (row.get("place_updated_at") != null) {
            place.setPlaceUpdatedAt(row.get("place_updated_at").toString());
        }
        return place;
    }
    
    private List<Map<String, Object>> selectPlaces() {
        String sql = "SELECT * FROM places";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }
    
    public JSONArray readPlacesJSON() {
        List<Map<String, Object>> resultList = selectPlaces();
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
        return jsonArray;
    }
    
    public void updatePlace(Place place) {
        String currentDatetime = Utility.getCurrentDatetime();
        String sql = "UPDATE places SET place_name=?, description=?, latitude=?, longitude=?, address=?, place_updated_at=? WHERE place_id=?";
        jdbcTemplate.update(sql, place.getPlaceName(), place.getDescription(), place.getLatitude(), place.getLongitude(), 
                place.getAddress(), place.getPlaceUpdatedAt(), place.getPlaceId()
        );
    }
    
    public List<Place> readPlacesForVisited(int userId) {
        String sql = "SELECT * FROM places WHERE places.user_id = ? OR places.place_id in (SELECT DISTINCT comments.place_id\n" +
"FROM comments WHERE  comments.user_id = ?)";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId, userId);
        List<Place> placeList = new ArrayList<Place>();
        for (int i = 0; i < resultList.size(); i++) {
            Place place = makePlaceFromRow(resultList.get(i));
            placeList.add(place);
        }
        System.out.println(userId);
        System.out.println(resultList.size());
        return placeList;
    }
}
