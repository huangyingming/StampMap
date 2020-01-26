package com.example.stampmap.dao;

import com.example.stampmap.dto.User;
import com.example.stampmap.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        User user = makeUserFromRow(resultList.get(0));
        if (user.getUserDeletedAt() != null) return null;
        return user;
    }
    
    private User makeUserFromRow(Map<String, Object> row) {
        User user = new User();
        user.setUserId(Integer.valueOf(row.get("user_id").toString()));
        user.setUserName(row.get("user_name").toString());
        user.setPassword(row.get("user_password").toString());
        if (row.get("user_deleted_at") == null) {
            user.setUserDeletedAt(null);
        } else {
            user.setUserDeletedAt(row.get("user_deleted_at").toString());
        };
        return user;
    }
    
    public User addUser(User user) throws DataIntegrityViolationException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO users "
             + "(user_id, user_name, user_password) VALUES(?, ?, ?)";
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException, DataIntegrityViolationException {
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
    
    public List<User> readUsers() {
        String sql = "SELECT * FROM users";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        List<User> userList = new ArrayList();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> row = resultList.get(i);
            User user = makeUserFromRow(row);
            userList.add(user);
        }
        return userList;
    }
    
    public List<User> readUsersWithQuery(String query) {
        String sql = "SELECT * FROM users WHERE user_name like ?";
        String strSearch = query.replaceAll("%","\\\\%").replaceAll("_","\\\\_");
        strSearch = "%" + strSearch + "%";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, strSearch);
        List<User> userList = new ArrayList();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> row = resultList.get(i);
            User user = makeUserFromRow(row);
            userList.add(user);
        }
        return userList;
    }
    
    public void deleteUser(int userId) {
        String sql = "DELETE FROM images WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
        sql = "DELETE FROM comments WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
        String currentDatetime = Utility.getCurrentDatetime();
        sql = "UPDATE users SET user_deleted_at = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, currentDatetime, userId);
    }
    
    public void updateUser(User user) {
        String sql = "UPDATE users SET user_name = ?, user_password = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getUserId());
    }
}
