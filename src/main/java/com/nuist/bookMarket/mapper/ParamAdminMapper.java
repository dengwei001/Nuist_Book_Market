package com.nuist.bookMarket.mapper;

import java.util.List;
import java.util.Map;

public interface ParamAdminMapper {

    List<Map<String,Object>> selColByColName(Map map);

    List<Map<String,Object>> selSpeBySpeName(Map map);

    List<Map<String,Object>> selPressByPressName(Map map);

    List<Map<String,Object>> selectCollege();

    List<Map<String,Object>> selectSpecialty(Map param);

    List<Map<String,Object>> selectPress();

    int insertCollege(Map map);

    int insertSpecialty(Map map);

    int insertPress(Map map);

    int delColByColCode(Map map);

    int delSpeBySpeCode(Map map);

    int delPressByPressCode(Map map);


    //辅书类型

    List<Map<String,Object>> selectType(Map map);

    int delTypeByTypeCode(Map map);

    int insertType(Map map);

    List<Map<String,Object>> selTypeByTypeName(Map map);

    //名著小说类
    List<Map<String,Object>> selectStyle(Map map);

    int delStyleByStyleCode(Map map);

    int insertStyle(Map map);

    List<Map<String,Object>> selStyleByStyleName(Map map);

    Map selectCollegeByCode(String COLLEGE_CODE);

    Map selectSpecialtyByCode(String SPECIALTY_CODE);

    Map selectPressByCode(String PRESS_CODE);

    Map selectTypeByCode(String TYPE_CODE);

    Map selectStyleByCode(String STYLE_CODE);
}
