package com.nuist.bookMarket.service;

import com.nuist.bookMarket.model.User;

import java.util.Map;

public interface UserService {
    User getUserByUserName(String username);

    User getUserByUserId(String userId);

    int insertUser(Map map);

    int changePassword(Map map);

    int changeMobile(Map map);
}
