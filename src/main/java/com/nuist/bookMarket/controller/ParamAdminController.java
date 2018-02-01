package com.nuist.bookMarket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.service.ParamAdminService;
import com.nuist.bookMarket.service.SequenceService;
import com.nuist.bookMarket.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/paramAdmin")
public class ParamAdminController {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ParamAdminService paramAdminService;

    @RequestMapping("/getCollege")
    @ResponseBody
    public Object getCollege(@RequestParam Map param){
        if (param.containsKey("page")){
            PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
            List list = paramAdminService.getCollege();
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
            resultMap.put("rows",list);
            return resultMap;
        }else {
            return paramAdminService.getCollege();
        }
    }

    @RequestMapping("/getSpecialty")
    @ResponseBody
    public Object getSpecialty(@RequestParam Map param){
        if (param.containsKey("page")){
            PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
            List list = paramAdminService.getSpecialty(param);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
            resultMap.put("rows",list);
            return resultMap;
        }else {
            return paramAdminService.getSpecialty(param);
        }

    }

    @RequestMapping("/getPress")
    @ResponseBody
    public Object getPress(@RequestParam Map param){
        if (param.containsKey("page")){
            PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
            List list = paramAdminService.getPress();
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
            resultMap.put("rows",list);
            return resultMap;
        }else {
            return paramAdminService.getPress();
        }
    }


    @RequestMapping("/insertCollege")
    @ResponseBody
    public Object insertCollege(@RequestParam Map param) throws Exception {
        param.put("COLLEGE",param.get("paramName"));
        //检验学院信息是否已存在
        List list = paramAdminService.getColByColName(param);
        if (list.size()>0){
            Map res = new HashMap();
            List webList = new ArrayList();
            res.put("answer","学院信息已经存在");
            webList.add(res);
            return webList;
        }
        String collegeCode = sequenceService.getCollegeCode();
        String createTime = DateUtils.getSysdate("yyyy-MM-dd HH:mm:ss");
        String endTime = "2050-12-31 23:59:59";
        param.put("COLLEGE_CODE",collegeCode);
        param.put("CREATE_TIME",createTime);
        param.put("END_TIME",endTime);
        return paramAdminService.insertCollege(param);
    }

    @RequestMapping("/insertSpecialty")
    @ResponseBody
    public Object insertSpecialty(@RequestParam Map param) throws Exception {
        param.put("SPECIALTY",param.get("paramName"));
        param.put("COLLEGE_CODE",param.get("extra"));
        //检验专业信息是否已存在
        List list = paramAdminService.getSpeBySpeName(param);
        if (list.size()>0){
            Map res = new HashMap();
            List webList = new ArrayList();
            res.put("answer","专业信息已经存在");
            webList.add(res);
            return webList;
        }
        String specialtyCode = sequenceService.getSpecialtyCode();
        String createTime = DateUtils.getSysdate("yyyy-MM-dd HH:mm:ss");
        String endTime = "2050-12-31 23:59:59";
        param.put("SPECIALTY_CODE",specialtyCode);
        param.put("CREATE_TIME",createTime);
        param.put("END_TIME",endTime);
        return paramAdminService.insertSpecialty(param);
    }

    @RequestMapping("/insertPress")
    @ResponseBody
    public Object insertPress(@RequestParam Map param) throws Exception {
        param.put("PRESS",param.get("paramName"));
        //检验出版社信息是否已存在
        List list = paramAdminService.getPressByPressName(param);
        if (list.size()>0){
            Map res = new HashMap();
            List webList = new ArrayList();
            res.put("answer","出版社信息已经存在");
            webList.add(res);
            return webList;
        }
        String pressCode = sequenceService.getPressCode();
        String createTime = DateUtils.getSysdate("yyyy-MM-dd HH:mm:ss");
        String endTime = "2050-12-31 23:59:59";
        param.put("PRESS_CODE",pressCode);
        param.put("CREATE_TIME",createTime);
        param.put("END_TIME",endTime);
        return paramAdminService.insertPress(param);
    }

    @RequestMapping("/delCollege")
    @ResponseBody
    public Object delCollege(@RequestParam Map param){
        param.put("COLLEGE_CODE",param.get("paramCode"));
        return paramAdminService.delColByCol(param);
    }

    @RequestMapping("/delSpecialty")
    @ResponseBody
    public Object delSpecialty(@RequestParam Map param){
        param.put("SPECIALTY_CODE",param.get("paramCode"));
        return paramAdminService.delSpeBySpe(param);
    }

    @RequestMapping("/delPress")
    @ResponseBody
    public Object delPress(@RequestParam Map param){
        param.put("PRESS_CODE",param.get("paramCode"));
        return paramAdminService.delPressByPress(param);
    }


    @RequestMapping("/getRefType")
    @ResponseBody
    public Object getRefType(@RequestParam Map param){
        if (param.containsKey("page")){
            PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
            List list = paramAdminService.getType(param);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
            resultMap.put("rows",list);
            return resultMap;
        }else {
            return paramAdminService.getType(param);
        }
    }

    @RequestMapping("/delType")
    @ResponseBody
    public Object delType(@RequestParam Map param){
        param.put("TYPE_CODE",param.get("paramCode"));
        return paramAdminService.delTypeByTypeCode(param);
    }

    @RequestMapping("/insertType")
    @ResponseBody
    public Object insertType(@RequestParam Map param) throws Exception {
        param.put("TYPE",param.get("paramName"));
        //检验出版社信息是否已存在
        List list = paramAdminService.getTypeByTypeName(param);
        if (list.size()>0){
            Map res = new HashMap();
            List webList = new ArrayList();
            res.put("answer","此类型已经存在");
            webList.add(res);
            return webList;
        }
        String pressCode = sequenceService.getTypeCode();
        String createTime = DateUtils.getSysdate("yyyy-MM-dd HH:mm:ss");
        String endTime = "2050-12-31 23:59:59";
        param.put("TYPE_CODE",pressCode);
        param.put("CREATE_TIME",createTime);
        param.put("END_TIME",endTime);
        return paramAdminService.insertType(param);
    }

    @RequestMapping("/getNovelStyle")
    @ResponseBody
    public Object getNovelStyle(@RequestParam Map param){
        if (param.containsKey("page")){
            PageHelper.startPage(Integer.parseInt(param.get("page").toString()),Integer.parseInt(param.get("rows").toString()),true);
            List list = paramAdminService.getStyle(param);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("total",Integer.parseInt(String.valueOf(new PageInfo(list).getTotal())));
            resultMap.put("rows",list);
            return resultMap;
        }else {
            return paramAdminService.getStyle(param);
        }
    }

    @RequestMapping("/delStyle")
    @ResponseBody
    public Object delStyle(@RequestParam Map param){
        param.put("STYLE_CODE",param.get("paramCode"));
        return paramAdminService.delStyleByStyleCode(param);
    }

    @RequestMapping("/insertStyle")
    @ResponseBody
    public Object insertStyle(@RequestParam Map param) throws Exception {
        param.put("STYLE",param.get("paramName"));
        //检验出版社信息是否已存在
        List list = paramAdminService.getStyleByStyleName(param);
        if (list.size()>0){
            Map res = new HashMap();
            List webList = new ArrayList();
            res.put("answer","此类型已经存在");
            webList.add(res);
            return webList;
        }
        String pressCode = sequenceService.getStyleCode();
        String createTime = DateUtils.getSysdate("yyyy-MM-dd HH:mm:ss");
        String endTime = "2050-12-31 23:59:59";
        param.put("STYLE_CODE",pressCode);
        param.put("CREATE_TIME",createTime);
        param.put("END_TIME",endTime);
        return paramAdminService.insertStyle(param);
    }

}
