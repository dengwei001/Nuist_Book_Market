package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface CombineService {

    List<Map<String,Object>> queryDetailByBookId(Map map);

    int insertDetail(Map map);

}
