package com.nuist.bookMarket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SequenceUtils {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public String getCollegeCode() throws Exception {
        String promotionId = getSingleSequence("college$seq");
        return promotionId;
    }

    public String getSpecialtyCode() throws Exception {
        String promotionId = getSingleSequence("specialty$seq");
        return promotionId;
    }

    public String getBookId() throws Exception {
        String promotionId = getSingleSequence("book$seq");
        return promotionId;
    }

    public String getPressCode() throws Exception {
        String promotionId = getSingleSequence("press$seq");
        return promotionId;
    }

    public String getTypeCode() throws Exception {
        String promotionId = getSingleSequence("type$seq");
        return promotionId;
    }

    public String getStyleCode() throws Exception {
        String promotionId = getSingleSequence("style$seq");
        return promotionId;
    }
    public String getUserId() throws Exception {
        String promotionId = getSingleSequence("user$seq");
        return promotionId;
    }



    private String getSingleSequence(String sequenceName) throws Exception {
        List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList("select seq_nextval('" + sequenceName + "') nextval from dual", new HashMap());
        if(CollectionUtils.isEmpty(resultList)) {
            throw new Exception("Please config " + sequenceName + " in table[sequence] first!");
        } else {
            String nextval = ((Map)resultList.get(0)).get("nextval").toString();
            if(nextval.equals("-1")) {
                throw new Exception("Please config " + sequenceName + " in table[sequence] first!");
            } else {
                return nextval;
            }
        }
    }
}
