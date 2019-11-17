package com.example.stampmap.dao;

import com.example.stampmap.dto.Place;
import com.example.stampmap.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
        
	
    public int insertPlace(Place place) {
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
           
}
