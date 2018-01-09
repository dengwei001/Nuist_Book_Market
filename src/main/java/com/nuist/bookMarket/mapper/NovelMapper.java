package com.nuist.bookMarket.mapper;

import java.util.List;
import java.util.Map;

public interface NovelMapper {

    List<Map<String,Object>> selectNovel(Map map);

    int insertNovel(Map map);

    List<Map<String,Object>> selectBySellerId(Map map);
}