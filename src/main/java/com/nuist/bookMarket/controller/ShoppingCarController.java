package com.nuist.bookMarket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.*;
import com.nuist.bookMarket.util.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/shoppingCar")
public class ShoppingCarController {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SchoolBookService schoolBookService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private NovelService novelService;
    @Autowired
    private CombineService combineService;
    @Autowired
    private JSONArrayService jsonArrayService;


    @RequestMapping("/addToCar")
    @ResponseBody
    public Object addToCar(@RequestParam Map param){
        Jedis jedis = jedisPool.getResource();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        try {
            String userCar = jedis.get(userId+"car");
            List list = new ArrayList();
            switch ((String )param.get("bookType")){
                case "schoolBook":
                    list = schoolBookService.queryBookAndDetailById(param);
                    break;
                case "reference":
                    list = referenceService.queryBookAndDetailById(param);
                    break;
                case "novel":
                    list = novelService.queryBookAndDetailById(param);
                    break;
            }
            JSONArray jsonArray;
            if (userCar!=null){
                jsonArray = JSONArray.parseArray(userCar);
                JSONObject jsonObject=jsonArrayService.query(jsonArray,"BOOK_ID", (String) param.get("BOOK_ID"));
                if (jsonObject!=null){
                    int number = Integer.parseInt(String.valueOf(jsonObject.get("NUM")))+Integer.parseInt(String.valueOf(param.get("number")));
                    jsonObject.put("NUM",number);
                    jsonArrayService.del(jsonArray,"BOOK_ID", (String) param.get("BOOK_ID"));
                    jsonArray.add(jsonObject);
                }else {
                    Map map= (Map) list.get(0);
                    map.put("NUM",param.get("number"));
                    jsonArray.add(map);
                }
            }else {
                jsonArray = new JSONArray();
                Map map = (Map) list.get(0);
                map.put("NUM",param.get("number"));
                jsonArray.add(map);
            }
            jedis.set(userId+"car", String.valueOf(jsonArray));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return "系统异常，该书籍可能已经不存在！";
        }finally {
            jedisPool.returnResource(jedis);
        }
    }

    @RequestMapping("/getCar")
    @ResponseBody
    public Object getCar(@RequestParam Map param){
        Jedis jedis = jedisPool.getResource();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        try {
            String userCar = jedis.get(userId+"car");
            JSONArray jsonArray;
            if (userCar != null) {
                jsonArray = JSONArray.parseArray(userCar);
            } else {
                jsonArray = new JSONArray();
            }
            return jsonArray;
        }catch (Exception e){
            e.printStackTrace();
            return new JSONArray();
        }finally {
            jedisPool.returnResource(jedis);
        }

    }

    @RequestMapping(value = "/delFromCar")
    @ResponseBody
    public Object delFromCar(@RequestBody String param){
        Jedis jedis = jedisPool.getResource();
        JSONArray jsonArray = JSONArray.parseArray(param);
        if (jsonArray.size()>0){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            String userId = user.getUserid();
            try {
                String userCar = jedis.get(userId+"car");
                JSONArray jsonCar = JSONArray.parseArray(userCar);
                for (int i=0;i<jsonArray.size();i++){
                    JSONObject book = (JSONObject) jsonArray.get(i);
                    jsonCar = jsonArrayService.del(jsonCar,"BOOK_ID", (String) book.get("BOOK_ID"));
                }
                jedis.set(userId+"car", String.valueOf(jsonCar));
                JSONObject result = new JSONObject();
                result.put("res","OK");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return new JSONObject();
            }finally {
                jedisPool.returnResource(jedis);
            }
        }else {
            JSONObject result = new JSONObject();
            result.put("res","请选择商品。");
            return result;
        }
    }

    @RequestMapping("/buyBook")
    @ResponseBody
    public Object buyBook(@RequestBody String param) throws Exception {
        Jedis jedis = jedisPool.getResource();

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        JSONArray jsonArray = JSONArray.parseArray(param);
        JSONObject result = new JSONObject();
        JSONArray lowStock = new JSONArray();
        JSONArray sellerOrder;
        JSONArray buyerOrder;
        try {
            String buyerOrderString = jedis.get(userId+"buyerOrder");
            if (buyerOrderString!=null){
                buyerOrder = JSONArray.parseArray(buyerOrderString);
            }else {
                buyerOrder = new JSONArray();
            }
            if (jsonArray.size()>0){
                String userCar = jedis.get(userId+"car");
                JSONArray jsonCar = JSONArray.parseArray(userCar);
                for (int i=0;i<jsonArray.size();i++){

                    JSONObject book = (JSONObject) jsonArray.get(i);
                    String sellerOrderString = jedis.get(book.get("SELLER_ID")+"sellerOrder");
                    if (sellerOrderString!=null){
                        sellerOrder = JSONArray.parseArray(sellerOrderString);
                    }else {
                        sellerOrder = new JSONArray();
                    }
                    List list = combineService.queryBookById(book);
                    if (list.size()<1){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("BOOK_ID",book.get("BOOK_ID"));
                        jsonObject.put("BOOK_NAME",book.get("BOOK_NAME"));
                        jsonObject.put("STOCK",0);
                        lowStock.add(jsonObject);
                    }else{
                        Map map = (Map) list.get(0);
                        int number = Integer.parseInt(String.valueOf(book.get("NUM")));
                        int stock = Integer.parseInt(String.valueOf(map.get("STOCK")));
                        if (number>stock){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("BOOK_ID",book.get("BOOK_ID"));
                            jsonObject.put("BOOK_NAME",book.get("BOOK_NAME"));
                            jsonObject.put("STOCK",stock);
                            lowStock.add(jsonObject);
                        }else {
                            Map order = orderService.createOrder(book,user);
                            sellerOrder.add(0,order);
                            buyerOrder.add(0,order);
                            int newStock = stock-number;
                            Map updateMap = new HashMap();
                            updateMap.put("BOOK_ID",book.get("BOOK_ID"));
                            updateMap.put("STOCK",newStock);
                            combineService.updateBookById(updateMap);
                            jsonCar = jsonArrayService.del(jsonCar,"BOOK_ID", (String) book.get("BOOK_ID"));
                            jedis.set(book.get("SELLER_ID")+"sellerOrder", String.valueOf(sellerOrder));
                        }
                    }
                }
                jedis.set(userId+"buyerOrder", String.valueOf(buyerOrder));
                jedis.set(userId+"car", String.valueOf(jsonCar));
                result.put("webResult",true);
                result.put("data",lowStock);
                return result;
            }else {
                result.put("webResult",false);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("webResult",false);
            return result;
        }finally {
            jedisPool.returnResource(jedis);
        }

    }

    @RequestMapping("/updateNum")
    @ResponseBody
    public Object updateNum(@RequestParam Map param){
        Jedis jedis = jedisPool.getResource();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();
        try {
            JSONArray jsonCar= JSONArray.parseArray(jedis.get(userId+"car"));
            JSONObject jsonObject = jsonArrayService.query(jsonCar,"BOOK_ID", (String) param.get("BOOK_ID"));
            jsonObject.put("NUM",param.get("NUM"));
            jsonArrayService.update(jsonCar,jsonObject,"BOOK_ID");
            jedis.set(userId+"car", String.valueOf(jsonCar));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            jedisPool.returnResource(jedis);
        }
    }


}
