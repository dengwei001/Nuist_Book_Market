package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface NovelService {

    List queryNovel(Map map);

    int insertNovel(Map map);

    List<Map<String,Object>> queryBookAndDetailBySellerId(Map map);

    List<Map<String,Object>> queryBookAndDetailById(Map map);

    int updateById(Map map);

    int deleteById(Map map);



}
