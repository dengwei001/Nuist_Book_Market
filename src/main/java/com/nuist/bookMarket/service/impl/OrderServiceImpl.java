package com.nuist.bookMarket.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.nuist.bookMarket.mapper.OrderMapper;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.JSONArrayService;
import com.nuist.bookMarket.service.OrderService;
import com.nuist.bookMarket.service.SequenceService;
import com.nuist.bookMarket.service.UserService;
import com.nuist.bookMarket.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private UserService userService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private Jedis jedis;
    @Autowired
    private JSONArrayService jsonArrayService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map createOrder(Map map, User custom) throws Exception {
        Map order = map;
        String createTime = DateUtils.getSysdate("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = sdf.format(calendar.getTime());
        User seller = userService.getUserByUserId((String) map.get("SELLER_ID"));
        String orderId = sequenceService.getOrderId();
        order.put("ORDER_ID", orderId);
        order.put("SELLER_MOBILE", seller.getMobile());
        order.put("CUSTOM_ID", custom.getUserid());
        order.put("CUSTOM", custom.getName());
        order.put("CUSTOM_MOBILE", custom.getMobile());
        order.put("CREATE_TIME", createTime);
        order.put("END_TIME", endTime);
        order.put("ORDER_STATE", "0");
        return order;
    }

    @Override
    public JSONArray getOrderFromRedis(String orderKey) {
        String order = jedis.get(orderKey);
        if (order!=null){
            return JSONArray.parseArray(order);
        }else {
            return new JSONArray();
        }
    }

    @Override
    public int batchImportOrder(List list) {
        return orderMapper.batchImportOrder(list);
    }
}
