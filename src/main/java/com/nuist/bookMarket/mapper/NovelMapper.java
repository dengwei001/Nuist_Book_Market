package com.nuist.bookMarket.mapper;

import java.util.List;
import java.util.Map;

public interface NovelMapper {

    List<Map<String,Object>> selectNovel(Map map);

    int insertNovel(Map map);

    List<Map<String,Object>> selectBookAndDetailBySellerId(Map map);

    List<Map<String,Object>> selectBookAndDetailById(Map map);

    int updateById(Map map);

    int deleteById(Map map);

}