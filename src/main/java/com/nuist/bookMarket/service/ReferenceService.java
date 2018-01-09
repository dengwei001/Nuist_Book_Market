package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface ReferenceService {

    List queryReference(Map map);

    int insertReference(Map map);

    List<Map<String,Object>> queryBySellerId(Map map);


}
