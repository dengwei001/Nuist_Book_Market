package com.nuist.bookMarket.mapper;


import java.util.List;
import java.util.Map;

public interface ReferenceMapper {

    List<Map<String,Object>> selectReference(Map map);

    int insertReference(Map map);

    List<Map<String,Object>> selectBookAndDetailBySellerId(Map map);

    List<Map<String,Object>> selectBookAndDetailById(Map map);

    int updateById(Map map);



}