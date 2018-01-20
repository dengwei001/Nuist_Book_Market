package com.nuist.bookMarket.service;

import com.alibaba.fastjson.JSONArray;
import com.nuist.bookMarket.model.User;

import java.util.Map;

public interface OrderService {

    Map createOrder(Map map, User custom) throws Exception;

    JSONArray getOrderFromRedis(String orderKey);
}
