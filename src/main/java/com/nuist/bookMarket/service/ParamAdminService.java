package com.nuist.bookMarket.service;

import java.util.List;
import java.util.Map;

public interface ParamAdminService {

    List<Map<String, Object>> getColByColName(Map map);

    List<Map<String, Object>> getSpeBySpeName(Map map);

    List<Map<String, Object>> getPressByPressName(Map map);

    List<Map<String, Object>> getCollege();

    List<Map<String, Object>> getSpecialty(Map param);

    List<Map<String, Object>> getPress();

    Object insertCollege(Map map);

    Object insertSpecialty(Map map);

    Object insertPress(Map map);

    int delColByCol(Map map);

    int delSpeBySpe(Map map);

    int delPressByPress(Map map);

    List getType(Map map);

    List getTypeByTypeName(Map map);

    int insertType(Map map);

    int delTypeByTypeCode(Map map);

    List getStyle(Map map);

    List getStyleByStyleName(Map map);

    int insertStyle(Map map);

    int delStyleByStyleCode(Map map);

    Map getCollegeByCode(String COLLEGE_COED);

    Map getSpecialtyByCode(String SPECIALTY_CODE);

    Map getPressByCode(String PRESS_CODE);

    Map getTypeByCode(String TYPE_CODE);

    Map getStyleByCode(String STYLE_CODE);

}
