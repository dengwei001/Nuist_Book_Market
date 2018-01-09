package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.NovelMapper;
import com.nuist.bookMarket.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelMapper novelMapper;

    @Override
    public List queryNovel(Map map) {
        return novelMapper.selectNovel(map);
    }

    @Override
    public int insertNovel(Map map) {
        return novelMapper.insertNovel(map);
    }

    @Override
    public List<Map<String, Object>> queryBySellerId(Map map) {
        return novelMapper.selectBySellerId(map);
    }
}
