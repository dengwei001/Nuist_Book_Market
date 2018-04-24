package com.nuist.bookMarket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface JSONArrayService {

    JSONArray del(JSONArray jsonArray, String delId, String delVal);

    JSONArray update(JSONArray jsonArray,JSONObject jsonObject,String id);

    JSONObject query(JSONArray jsonArray,String id,String value);

    JSONArray queryJSONArray(JSONArray jsonArray, String id,String value);
}
