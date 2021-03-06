package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.UserMapper;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    @Override
    public List getUserList(Map param) {
        return userMapper.selectUserByParam(param);
    }

    @Override
    public int insertUser(Map map) {
        return userMapper.insertUser(map);
    }

    @Override
    public int changePassword(Map map) {
        return userMapper.updateUser(map);
    }

    @Override
    public int changeMobile(Map map) {
        return userMapper.updateUser(map);
    }

    @Override
    public int setAdmin(Map map) {
        return userMapper.updateUser(map);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public List getUserId() {
        return userMapper.selectId();
    }

    @Override
    public List<Map<String, Object>> getAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public int deleteUserById(Map map) {
        return userMapper.delUserById(map);
    }
}
