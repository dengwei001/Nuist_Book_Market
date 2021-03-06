package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.SchoolBookMapper;
import com.nuist.bookMarket.service.SchoolBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("schoolBookService")
public class SchoolBookServiceImpl implements SchoolBookService{

    @Autowired
    private SchoolBookMapper schoolBookMapper;

    public List getAllSchoolBook(){
        List list = schoolBookMapper.selectAll();
        return list;
    }

    @Override
    public List<Map<String,Object>> querySchoolBook(Map map) {
        return schoolBookMapper.querySchoolBook(map);
    }

    @Override
    public int insertSchoolBook(Map map) {
        return schoolBookMapper.insertSchoolBook(map);
    }

    @Override
    public List<Map<String, Object>> queryBookAndDetailBySellerId(Map map) {
        return schoolBookMapper.selectBookAndDetailBySellerId(map);
    }

    @Override
    public List<Map<String, Object>> queryBookAndDetailById(Map map) {
        return schoolBookMapper.selectBookAndDetailById(map);
    }

    @Override
    public int updateById(Map map) {
        return schoolBookMapper.updateById(map);
    }

    @Override
    public int deleteById(Map map) {
        return schoolBookMapper.deleteById(map);
    }
}
