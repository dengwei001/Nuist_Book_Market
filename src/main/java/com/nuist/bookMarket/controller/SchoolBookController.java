package com.nuist.bookMarket.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.service.impl.SchoolBookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/schoolBook")
public class SchoolBookController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SchoolBookServiceImpl schoolBookService;


    @RequestMapping("/getBook")
    @ResponseBody
    public Object getBook(@RequestParam Map param){
        PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
        List list =  schoolBookService.getAllSchoolBook();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
        resultMap.put("rows",list);
        logger.debug(resultMap.toString());
        return resultMap;
    }

    @RequestMapping("/querySchoolBook")
    @ResponseBody
    public Object querySchoolBook(@RequestParam Map param){
        PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
        if (param.get("COLLEGE").equals("全部")){
            param.put("COLLEGE",null);
        }
        List list = schoolBookService.querySchoolBook(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
        resultMap.put("rows",list);
        logger.debug(resultMap.toString());
        return resultMap;
    }


}
