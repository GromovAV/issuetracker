package com.axmor.dao;

import com.axmor.model.User;

public interface UserDao {
    void registerUser(User user);
    User getUserByLogin(String login);
    User getUserByID(int id);
}
