package com.nuist.bookMarket.mapper;


import com.nuist.bookMarket.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User selectByUserName(String username);

    List<Map> selectAll();

    int insertUser(Map map);

    int updateUser(Map map);
}