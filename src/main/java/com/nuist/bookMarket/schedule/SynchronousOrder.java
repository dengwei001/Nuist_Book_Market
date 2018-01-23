package com.nuist.bookMarket.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuist.bookMarket.service.OrderService;
import com.nuist.bookMarket.service.UserService;
import com.nuist.bookMarket.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

@Component
public class SynchronousOrder {

    @Autowired
    private Jedis jedis;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Scheduled(fixedRate = 1000*3600)
    public void importOrderFromRedisToSql()throws Exception{
        System.out.println(DateUtils.getSysdate("yyyy-MM-dd hh:mm:ss"));
        List userIdList = userService.getUserId();
        JSONArray order = new JSONArray();
        for (int i=0;i<userIdList.size();i++){
            Map map = (Map) userIdList.get(i);
            JSONArray jsonArray = orderService.getOrderFromRedis(map.get("userId")+"sellerOrder");
            order.addAll(jsonArray);
        }
        orderService.batchImportOrder(order);

    }
}
