package com.nuist.bookMarket.mapper;


import java.util.List;
import java.util.Map;

public interface ReferenceMapper {

    List<Map<String,Object>> selectReference(Map map);

    int insertReference(Map map);

    List<Map<String,Object>> selectBySellerId(Map map);


}