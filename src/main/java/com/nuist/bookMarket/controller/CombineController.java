package com.nuist.bookMarket.controller;

import com.nuist.bookMarket.service.CombineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/combine")
public class CombineController {

    @Autowired
    private CombineService combineService;

    @RequestMapping("/getDetail")
    @ResponseBody
    public Object getDetail(@RequestParam Map param ){
        return combineService.queryDetailByBookId(param);
    }
}
