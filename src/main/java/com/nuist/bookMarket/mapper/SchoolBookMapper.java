package com.nuist.bookMarket.mapper;

import java.util.List;
import java.util.Map;

public interface SchoolBookMapper {

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> querySchoolBook(Map map);

    int insertSchoolBook(Map map);

    List<Map<String,Object>> selectBookAndDetailBySellerId(Map map);

    List<Map<String,Object>> selectBookAndDetailById(Map map);

    int updateById(Map map);

    int deleteById(Map map);

}