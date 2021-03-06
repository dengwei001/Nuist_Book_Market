package com.nuist.bookMarket.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelService novelService;

    @RequestMapping("/queryNovel")
    @ResponseBody
    public Object queryNovel(@RequestParam Map param){
        PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
        List list = novelService.queryNovel(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
        resultMap.put("rows",list);
        return resultMap;
    }



}
