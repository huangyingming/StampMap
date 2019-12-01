package com.example.stampmap.dao;

import com.example.stampmap.dto.User;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public User checkLogin(String userName, String password) {
        String sql = "SELECT * FROM users WHERE user_name=? AND user_password=? LIMIT 1";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userName, password);
        if (resultList.size() == 0) return null;
        return populateUserFromRow(resultList.get(0));
    }
    
    private User populateUserFromRow(Map<String, Object> row) {
        User user = new User();
        user.setUserId(Integer.valueOf(row.get("user_id").toString()));
        user.setUserName(row.get("user_name").toString());
        user.setPassword(row.get("user_password").toString());
        return user;
    }
}
