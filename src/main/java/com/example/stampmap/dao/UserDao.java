package com.example.stampmap.dao;

import com.example.stampmap.dto.User;

public interface UserDao {
    User checkLogin(String userName, String password);
}
