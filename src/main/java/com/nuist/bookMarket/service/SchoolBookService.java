package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface SchoolBookService {

    List<Map<String, Object>> getAllSchoolBook();

    List<Map<String,Object>> querySchoolBook(Map map);

    int insertSchoolBook(Map map);

    List<Map<String,Object>> queryBookAndDetailBySellerId(Map map);

    List<Map<String,Object>> queryBookAndDetailById(Map map);

    int updateById(Map map);

    int deleteById(Map map);


}
