package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.ReferenceMapper;
import com.nuist.bookMarket.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    ReferenceMapper referenceMapper;

    @Override
    public List queryReference(Map map) {
        return referenceMapper.selectReference(map);
    }

    @Override
    public int insertReference(Map map) {
        return referenceMapper.insertReference(map);
    }

    @Override
    public List<Map<String, Object>> queryBySellerId(Map map) {
        return referenceMapper.selectBySellerId(map);
    }
}
