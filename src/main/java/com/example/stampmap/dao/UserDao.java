package com.example.stampmap.dao;

import com.example.stampmap.dto.User;
import java.util.List;
public interface UserDao {
    User checkLogin(String userName, String password);
    User addUser(User user);
    List<User> readUsers();
    List<User> readUsersWithQuery(String query);
    void deleteUser(int UserId);
}
