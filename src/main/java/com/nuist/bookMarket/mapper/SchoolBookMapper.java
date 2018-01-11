package com.nuist.bookMarket.mapper;

import java.util.List;
import java.util.Map;

public interface SchoolBookMapper {

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> querySchoolBook(Map map);

    int insertSchoolBook(Map map);

    List<Map<String,Object>> selectBySellerId(Map map);


}