package com.example.stampmap.dao;

import com.example.stampmap.dto.User;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

public interface UserDao {
    User checkLogin(String userName, String password);
    User addUser(User user) throws DataIntegrityViolationException;
    List<User> readUsers();
    List<User> readUsersWithQuery(String query);
    void deleteUser(int UserId);
    void updateUser(User user);
}
