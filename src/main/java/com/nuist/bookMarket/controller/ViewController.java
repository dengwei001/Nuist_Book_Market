package com.nuist.bookMarket.controller;

import com.nuist.bookMarket.model.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ViewController {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping({"/home","/"})
    public String tohome(){
        return "home";
    }

    @RequestMapping("/schoolbook")
    public String toBookList(){
        return "schoolbook";
    }

    @RequestMapping("/userCenter")
    public String toUserCenter(){
        return "userCenter";
    }

    @RequestMapping("/novel")
    public String toNovel(){
        return "novel";
    }

    @RequestMapping("/reference")
    public String toReference(){
        return "reference";
    }

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/paramAdmin")
    public String toParamAdmin(){
        return "paramAdmin";
    }

    @RequestMapping("/myStore")
    public String toMyStore(){
        return "myStore";
    }

    @RequestMapping("/unRight")
    public String toUnright(){
        return "unRight";
    }

    @RequestMapping("/baseInfo")
    public String toBaseInfo(){
        return "baseInfo";
    }

    @RequestMapping("/shoppingCar")
    public String toShoppingCar(){
        return "shoppingCar";
    }

    @RequestMapping("/test")
    public String toTest(){
        return "test";
    }

    @RequestMapping("/orderCenter")
    public String toOrderCenter(){
        return "orderCenter";
    }

    @RequestMapping("/administrstor")
    public String toAdministrstor(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user.getUserrole().equals("admin")||user.getUserrole().equals("superAdmin")){
            return "administrstor";
        }else {
            return "unRight";
        }
    }

    @RequestMapping("/userManager")
    public String toUserManager(){
        return "userManager";
    }


    @RequestMapping("/getUserName")
    @ResponseBody
    public String getUserName(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getName();
    }




}
