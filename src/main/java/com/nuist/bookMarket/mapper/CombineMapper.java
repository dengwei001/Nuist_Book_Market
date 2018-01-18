package com.nuist.bookMarket.mapper;

import java.util.List;
import java.util.Map;

public interface CombineMapper {

    List<Map<String,Object>> selectByBookId(Map map);

    int insertDetail(Map map);

    List selectBookById(Map map);

}
