package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.CombineMapper;
import com.nuist.bookMarket.service.CombineService;
import com.nuist.bookMarket.service.NovelService;
import com.nuist.bookMarket.service.ReferenceService;
import com.nuist.bookMarket.service.SchoolBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CombineServiceImpl implements CombineService {

    @Autowired
    private CombineMapper combineMapper;
    @Autowired
    private SchoolBookService schoolBookService;
    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private NovelService novelService;

    @Override
    public List<Map<String, Object>> queryDetailByBookId(Map map) {
        return combineMapper.selectByBookId(map);
    }

    @Override
    public int insertDetail(Map map) {
        return combineMapper.insertDetail(map);
    }

    @Override
    public List<Map<String, Object>> queryBookById(Map map) {
        return combineMapper.selectBookById(map);
    }

    @Override
    public int updateBookById(Map map) {
        int sc = schoolBookService.updateById(map);
        int re = referenceService.updateById(map);
        int no = novelService.updateById(map);
        if (sc==1||re==1||no==1){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int updateDetailById(Map map) {
        return combineMapper.updateDetailById(map);
    }

    @Override
    public int deleteBookById(Map map) {
        int sc = schoolBookService.deleteById(map);
        int re = referenceService.deleteById(map);
        int no = novelService.deleteById(map);
        if (sc==1||re==1||no==1){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int deleteDetailById(Map map) {
        return combineMapper.deleteDetailById(map);
    }
}
