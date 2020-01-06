package com.example.stampmap.dao;

import com.example.stampmap.dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public User checkLogin(String userName, String password) {
        String sql = "SELECT * FROM users WHERE user_name=? AND user_password=? LIMIT 1";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userName, password);
        if (resultList.size() == 0) return null;
        return makeUserFromRow(resultList.get(0));
    }
    
    private User makeUserFromRow(Map<String, Object> row) {
        User user = new User();
        user.setUserId(Integer.valueOf(row.get("user_id").toString()));
        user.setUserName(row.get("user_name").toString());
        user.setPassword(row.get("user_password").toString());
        return user;
    }
    
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO users "
             + "(user_id, user_name, user_password) VALUES(?, ?, ?)";
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = 
                        connection.prepareStatement(sql, new String[] {"place_id"});
                    ps.setObject(1, null);
                    ps.setString(2, user.getUserName());
                    ps.setString(3, user.getPassword());
                    return ps;
                }
            }, keyHolder
        );
        int lastInsertedId = keyHolder.getKey().intValue();
        user.setUserId(lastInsertedId);
        return user;
    }
}
