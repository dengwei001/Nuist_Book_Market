package com.nuist.bookMarket.service;

import com.nuist.bookMarket.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getUserByUserName(String username);

    List getUserList(Map param);

    User getUserByUserId(String userId);

    int insertUser(Map map);

    int changePassword(Map map);

    int changeMobile(Map map);

    int setAdmin(Map map);

    List<Map<String,Object>> getUserId();

    List<Map<String,Object>> getAllUser();

    int deleteUserById(Map map);
}
