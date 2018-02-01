package com.nuist.bookMarket.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.CombineService;
import com.nuist.bookMarket.service.JSONArrayService;
import com.nuist.bookMarket.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderCenter")
public class OrderCenterController {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JSONArrayService jsonArrayService;
    @Autowired
    private CombineService combineService;

    @RequestMapping("/getOrder")
    @ResponseBody
    public Object getOrder(@RequestParam Map param){
        int page = Integer.parseInt((String) param.get("page"));
        int rows = Integer.parseInt((String) param.get("rows"));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        JSONArray buyerOrder = orderService.getOrderFromRedis(userId+param.get("order"));
        int total = buyerOrder.size();
        List list;
        if (page*rows>total){
            list = buyerOrder.subList((page-1)*rows,buyerOrder.size());
        }else {
            list = buyerOrder.subList((page-1)*rows,page*rows);
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",total);
        resultMap.put("rows",list);
        return resultMap;
    }

    @RequestMapping("/confirmTransaction")
    @ResponseBody
    public Object confirmTransaction(@RequestParam Map param){
        Jedis jedis = jedisPool.getResource();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        JSONArray sellerOrder = orderService.getOrderFromRedis(userId+"sellerOrder");
        JSONObject jsonSeller = jsonArrayService.query(sellerOrder,"ORDER_ID", (String) param.get("ORDER_ID"));
        jsonSeller.put("ORDER_STATE","1");
        JSONArray buyerOrder = orderService.getOrderFromRedis(jsonSeller.get("CUSTOM_ID")+"buyerOrder");
        JSONObject jsonBuyer = jsonArrayService.query(buyerOrder,"ORDER_ID",(String) param.get("ORDER_ID"));
        jsonBuyer.put("ORDER_STATE","1");
        jsonArrayService.update(sellerOrder,jsonSeller,"ORDER_ID");
        jsonArrayService.update(buyerOrder,jsonBuyer,"ORDER_ID");
        jedis.set(userId+"sellerOrder", String.valueOf(sellerOrder));
        jedis.set(jsonSeller.get("CUSTOM_ID")+"buyerOrder", String.valueOf(buyerOrder));
        return true;
    }

    @RequestMapping("/confirmReceiving")
    @ResponseBody
    public Object confirmReceiving(@RequestParam Map param){
        Jedis jedis = jedisPool.getResource();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        JSONArray buyerOrder = orderService.getOrderFromRedis(userId+"buyerOrder");
        JSONObject jsonBuyer = jsonArrayService.query(buyerOrder,"ORDER_ID", (String) param.get("ORDER_ID"));
        JSONArray sellerOrder = orderService.getOrderFromRedis(jsonBuyer.get("SELLER_ID")+"sellerOrder");
        JSONObject jsonSeller = jsonArrayService.query(sellerOrder,"ORDER_ID",(String) param.get("ORDER_ID"));
        jsonBuyer.put("ORDER_STATE","2");
        jsonSeller.put("ORDER_STATE","2");
        jsonArrayService.del(sellerOrder,"ORDER_ID", (String) param.get("ORDER_ID"));
        jsonArrayService.del(buyerOrder,"ORDER_ID", (String) param.get("ORDER_ID"));
        sellerOrder.add(jsonSeller);
        buyerOrder.add(jsonBuyer);
        jedis.set(userId+"buyerOrder", String.valueOf(buyerOrder));
        jedis.set(jsonBuyer.get("SELLER_ID")+"sellerOrder", String.valueOf(sellerOrder));
        return true;
    }

    @RequestMapping("/cancelOrder")
    @ResponseBody
    public Object cancelOrder(@RequestParam Map param){
        Jedis jedis = jedisPool.getResource();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        String bookId = null;
        switch ((String)param.get("ROLE")){
            case "buyer":
                JSONArray buyerOrder1 = orderService.getOrderFromRedis(userId+"buyerOrder");
                JSONObject jsonBuyer1 = jsonArrayService.query(buyerOrder1,"ORDER_ID", (String) param.get("ORDER_ID"));
                JSONArray sellerOrder1 = orderService.getOrderFromRedis(jsonBuyer1.get("SELLER_ID")+"sellerOrder");
                JSONObject jsonSeller1 = jsonArrayService.query(sellerOrder1,"ORDER_ID",(String) param.get("ORDER_ID"));
                jsonBuyer1.put("ORDER_STATE","3");
                jsonSeller1.put("ORDER_STATE","3");
                jsonArrayService.del(sellerOrder1,"ORDER_ID", (String) param.get("ORDER_ID"));
                jsonArrayService.del(buyerOrder1,"ORDER_ID", (String) param.get("ORDER_ID"));
                sellerOrder1.add(jsonSeller1);
                buyerOrder1.add(jsonBuyer1);
                jedis.set(userId+"buyerOrder", String.valueOf(buyerOrder1));
                jedis.set(jsonBuyer1.get("SELLER_ID")+"sellerOrder", String.valueOf(sellerOrder1));
                bookId= (String) jsonBuyer1.get("BOOK_ID");
                break;
            case "seller":
                JSONArray sellerOrder2 = orderService.getOrderFromRedis(userId+"sellerOrder");
                JSONObject jsonSeller2 = jsonArrayService.query(sellerOrder2,"ORDER_ID", (String) param.get("ORDER_ID"));
                jsonSeller2.put("ORDER_STATE","3");
                JSONArray buyerOrder2 = orderService.getOrderFromRedis(jsonSeller2.get("CUSTOM_ID")+"buyerOrder");
                JSONObject jsonBuyer2 = jsonArrayService.query(buyerOrder2,"ORDER_ID",(String) param.get("ORDER_ID"));
                jsonBuyer2.put("ORDER_STATE","3");
                jsonArrayService.del(sellerOrder2,"ORDER_ID", (String) param.get("ORDER_ID"));
                jsonArrayService.del(buyerOrder2,"ORDER_ID", (String) param.get("ORDER_ID"));
                sellerOrder2.add(jsonSeller2);
                buyerOrder2.add(jsonBuyer2);
                jedis.set(userId+"sellerOrder", String.valueOf(sellerOrder2));
                jedis.set(jsonSeller2.get("CUSTOM_ID")+"buyerOrder", String.valueOf(buyerOrder2));
                bookId = (String) jsonSeller2.get("BOOK_ID");
                break;
        }
        Map map = new HashMap();
        map.put("BOOK_ID",bookId);
        List list = combineService.queryBookById(map);
        int stock=0;
        if (list.size()>0){
            Map book = (Map) list.get(0);
            stock = Integer.parseInt(String.valueOf(book.get("STOCK"))) + Integer.parseInt(String.valueOf(param.get("NUM")));
        }else {
            stock = Integer.parseInt(String.valueOf(param.get("NUM")));
        }
        map.put("STOCK",stock);
        combineService.updateBookById(map);
        return true;
    }




}
