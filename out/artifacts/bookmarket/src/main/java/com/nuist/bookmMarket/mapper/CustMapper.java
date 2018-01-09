package com.nuist.bookmMarket.mapper;

import com.nuist.bookmMarket.model.Cust;

import java.util.List;
import java.util.Map;

public interface CustMapper {
    int deleteByPrimaryKey(Integer custId);

    int insert(Cust record);

    int insertSelective(Cust record);

    List<Map> selectByPrimaryKey(Integer custId);

    List<Map> selectAll();

    int updateByPrimaryKeySelective(Cust record);

    int updateByPrimaryKey(Cust record);
}