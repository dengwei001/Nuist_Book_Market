package com.nuist.bookMarket.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.UserService;
import com.nuist.bookMarket.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/userManager")
public class UserManagerController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserName(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userRole",user.getUserrole());
        jsonObject.put("userId",user.getUserid());
        jsonObject.put("name",user.getName());
        jsonObject.put("mobile",user.getMobile());
        return jsonObject;
    }

    @RequestMapping("/getAllUser")
    @ResponseBody
    public Object getAllUser(@RequestParam Map param){
        PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
        List list = userService.getAllUser();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
        resultMap.put("rows",list);
        return resultMap;
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public Object deleteUser(@RequestParam Map param){
        return userService.deleteUserById(param);
    }

    @RequestMapping("/setAdmin")
    @ResponseBody
    public Object setAdmin(@RequestParam Map param){
        param.put("userRole","administrator");
        return userService.setAdmin(param);
    }

    @RequestMapping("/cancelAdmin")
    @ResponseBody
    public Object cancelAdmin(@RequestParam Map param){
        param.put("userRole","commonUser");
        return userService.setAdmin(param);
    }

    @RequestMapping("/getUserListByParam")
    @ResponseBody
    public Object getUserListByParam(@RequestParam Map param){
        PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
        if ("all".equals(param.get("USER_ROLE").toString())){
            param.put("USER_ROLE",null);
        }
        List list = userService.getUserList(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
        resultMap.put("rows",list);
        return resultMap;
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Object changePassword(@RequestParam Map param){
        String newPassword = MD5Utils.md5((String) param.get("newPassword"));
        Map map = new HashMap();
        map.put("password",newPassword);
        map.put("userId",param.get("userId"));
        return userService.changePassword(map);
    }







}
