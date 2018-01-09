package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.mapper.ParamAdminMapper;
import com.nuist.bookMarket.service.ParamAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParamAdminServiceImpl implements ParamAdminService {

    @Autowired
    private ParamAdminMapper paramAdminMapper;


    @Override
    public List<Map<String, Object>> getColByColName(Map map) {
        return paramAdminMapper.selColByColName(map);
    }

    @Override
    public List<Map<String, Object>> getSpeBySpeName(Map map) {
        return paramAdminMapper.selSpeBySpeName(map);
    }

    @Override
    public List<Map<String, Object>> getPressByPressName(Map map) {
        return paramAdminMapper.selPressByPressName(map);
    }

    @Override
    public List<Map<String, Object>> getCollege() {
        return paramAdminMapper.selectCollege();
    }

    @Override
    public List<Map<String, Object>> getSpecialty(Map param) {
        return paramAdminMapper.selectSpecialty(param);
    }

    @Override
    public List<Map<String, Object>> getPress() {
        return paramAdminMapper.selectPress();
    }


    @Override
    public Object insertCollege(Map map) {
        return paramAdminMapper.insertCollege(map);
    }

    @Override
    public Object insertSpecialty(Map map) {
        return paramAdminMapper.insertSpecialty(map);
    }

    @Override
    public Object insertPress(Map map) {
        return paramAdminMapper.insertPress(map);
    }

    @Override
    public int delColByCol(Map map) {
        return paramAdminMapper.delColByColCode(map);
    }

    @Override
    public int delSpeBySpe(Map map) {
        return paramAdminMapper.delSpeBySpeCode(map);
    }

    @Override
    public int delPressByPress(Map map) {
        return paramAdminMapper.delPressByPressCode(map);
    }

    @Override
    public List getType(Map map) {
        return paramAdminMapper.selectType(map);
    }

    @Override
    public List getTypeByTypeName(Map map) {
        return paramAdminMapper.selTypeByTypeName(map);
    }

    @Override
    public int insertType(Map map) {
        return paramAdminMapper.insertType(map);
    }

    @Override
    public int delTypeByTypeCode(Map map) {
        return paramAdminMapper.delTypeByTypeCode(map);
    }

    @Override
    public List getStyle(Map map) {
        return paramAdminMapper.selectStyle(map);
    }

    @Override
    public List getStyleByStyleName(Map map) {
        return paramAdminMapper.selStyleByStyleName(map);
    }

    @Override
    public int insertStyle(Map map) {
        return paramAdminMapper.insertStyle(map);
    }

    @Override
    public int delStyleByStyleCode(Map map) {
        return paramAdminMapper.delStyleByStyleCode(map);
    }

    @Override
    public Map getCollegeByCode(String COLLEGE_CODE) {
        return paramAdminMapper.selectCollegeByCode(COLLEGE_CODE);
    }

    @Override
    public Map getSpecialtyByCode(String SPECIALTY_CODE) {
        return paramAdminMapper.selectSpecialtyByCode(SPECIALTY_CODE);
    }

    @Override
    public Map getPressByCode(String PRESS_CODE) {
        return paramAdminMapper.selectPressByCode(PRESS_CODE);
    }

    @Override
    public Map getTypeByCode(String TYPE_CODE) {
        return paramAdminMapper.selectTypeByCode(TYPE_CODE);
    }

    @Override
    public Map getStyleByCode(String STYLE_CODE) {
        return paramAdminMapper.selectStyleByCode(STYLE_CODE);
    }
}
