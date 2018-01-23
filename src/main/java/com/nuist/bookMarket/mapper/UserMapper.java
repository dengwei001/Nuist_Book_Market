package com.nuist.bookMarket.mapper;


import com.nuist.bookMarket.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User selectByUserName(String username);

    User selectByUserId(String userId);

    List<Map<String,Object>> selectAll();

    int insertUser(Map map);

    int updateUser(Map map);

    List<Map<String,Object>> selectId();
}