package com.nuist.bookMarket.controller;

import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.SequenceService;
import com.nuist.bookMarket.service.UserService;
import com.nuist.bookMarket.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;
    @Autowired
    SequenceService sequenceService;

    @RequestMapping("/registerUser")
    @ResponseBody
    public Object registerUser(@RequestParam Map param) throws Exception {
        User user = userService.getUserByUserName((String)param.get("username"));
        //判断用户是否存在
        if (user==null){
            String userId = sequenceService.getUserId();
            param.put("userId",userId);
            param.put("userRole","commonUser");
            String passwordMD5 = MD5Utils.md5((String) param.get("password"));
            param.put("password",passwordMD5);
            return userService.insertUser(param);
        }else{
            return false;
        }
    }

}
