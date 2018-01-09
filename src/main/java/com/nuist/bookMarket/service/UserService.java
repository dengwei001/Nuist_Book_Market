package com.nuist.bookMarket.service;

import com.nuist.bookMarket.model.User;

import java.util.Map;

public interface UserService {
    User getUserByUserName(String username);

    int insertUser(Map map);
}
