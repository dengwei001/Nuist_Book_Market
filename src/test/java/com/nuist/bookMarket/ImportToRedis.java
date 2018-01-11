package com.nuist.bookMarket;

import com.alibaba.fastjson.JSONArray;
import com.nuist.bookMarket.mapper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportToRedis {

    @Autowired
    private SchoolBookMapper schoolBookMapper;
    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private CombineMapper combineMapper;
    @Autowired
    private ParamAdminMapper paramAdminMapper;
    @Autowired
    private Jedis jedis;

    //同步mysql数据到Redis
    @Test
    public void import2redis(){
        Map map = new HashMap();
        //获取mysql数据
        List schoolBookList = schoolBookMapper.querySchoolBook(map);
        List referenceList = referenceMapper.selectReference(map);
        List novelList = novelMapper.selectNovel(map);
        List collegeList = paramAdminMapper.selectCollege();
        List specialtyList = paramAdminMapper.selectAllSpecialty();
        List typeList = paramAdminMapper.selectType(map);
        List styleList = paramAdminMapper.selectStyle(map);
        List pressList = paramAdminMapper.selectPress();
        //转为json数据
        JSONArray schoolBookJson = new JSONArray(schoolBookList);
        JSONArray referenceJson = new JSONArray(referenceList);
        JSONArray novelJson = new JSONArray(novelList);
        JSONArray collegeJson = new JSONArray(collegeList);
        JSONArray specialtyJson = new JSONArray(specialtyList);
        JSONArray typeJson = new JSONArray(typeList);
        JSONArray styleJson = new JSONArray(styleList);
        JSONArray pressJson = new JSONArray(pressList);
        //导入redis
        jedis.set("schoolBook", String.valueOf(schoolBookJson));
        jedis.set("reference", String.valueOf(referenceJson));
        jedis.set("novel", String.valueOf(novelJson));
        jedis.set("college", String.valueOf(collegeJson));
        jedis.set("specialty", String.valueOf(specialtyJson));
        jedis.set("press", String.valueOf(pressJson));
        jedis.set("type", String.valueOf(typeJson));
        jedis.set("style", String.valueOf(styleJson));

    }




}
