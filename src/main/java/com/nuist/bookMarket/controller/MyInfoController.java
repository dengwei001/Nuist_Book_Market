package com.nuist.bookMarket.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.*;
import com.nuist.bookMarket.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/myInfo")
public class MyInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public Object getUser(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Map userMap = new HashMap();
            userMap.put("userId",user.getUserid());
            userMap.put("username",user.getUsername());
            userMap.put("name",user.getName());
            userMap.put("mobile",user.getMobile());
            userMap.put("userRole",user.getUserrole());
        return (JSON.toJSON(userMap));
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Object changePassword(@RequestParam Map param){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String oldPassword = MD5Utils.md5((String) param.get("oldPassword"));
        String newPassword = MD5Utils.md5((String) param.get("newPassword"));
        if (user.getPassword().equals(oldPassword)){
            Map map = new HashMap();
            map.put("password",newPassword);
            map.put("userId",user.getUserid());
            return userService.changePassword(map);
        }else {
            return "原密码不正确！";
        }
    }

    @RequestMapping("/changeMobile")
    @ResponseBody
    public Object changeMobile(@RequestParam Map param){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Map map = new HashMap();
        map.put("mobile",param.get("newMobile"));
        map.put("userId",user.getUserid());
        return userService.changeMobile(map);
    }
}
