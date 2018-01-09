package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.CombineMapper;
import com.nuist.bookMarket.service.CombineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CombineServiceImpl implements CombineService {

    @Autowired
    private CombineMapper combineMapper;

    @Override
    public List<Map<String, Object>> queryDetailByBookId(Map map) {
        return combineMapper.selectByBookId(map);
    }

    @Override
    public int insertDetail(Map map) {
        return combineMapper.insertDetail(map);
    }
}
