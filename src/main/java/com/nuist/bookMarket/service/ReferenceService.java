package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface ReferenceService {

    List queryReference(Map map);

    int insertReference(Map map);

    List<Map<String,Object>> queryBookAndDetailBySellerId(Map map);

    List<Map<String,Object>> queryBookAndDetailById(Map map);

    int updateById(Map map);

    int deleteById(Map map);




}
