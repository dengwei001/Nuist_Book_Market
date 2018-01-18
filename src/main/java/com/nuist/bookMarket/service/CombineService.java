package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface CombineService {

    List<Map<String,Object>> queryDetailByBookId(Map map);

    int insertDetail(Map map);

    List<Map<String,Object>> queryBookById(Map map);

    int updateBookById(Map map);

}
