package com.nuist.bookMarket.service.impl;

import com.nuist.bookMarket.service.SequenceService;
import com.nuist.bookMarket.util.DateUtils;
import com.nuist.bookMarket.util.SequenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceServiceImpl implements SequenceService {

    private static String PRE_DATE_FORMAT = "YYMMdd";


    @Autowired
    private SequenceUtils sequenceUtils;

    @Override
    public String getBookId() throws Exception {
        String bookId = sequenceUtils.getBookId();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+bookId;
    }

    @Override
    public String getCollegeCode() throws Exception {
        String collegeCode = sequenceUtils.getCollegeCode();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+collegeCode;
    }

    @Override
    public String getSpecialtyCode() throws Exception {
        String specialtyCode = sequenceUtils.getSpecialtyCode();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+specialtyCode;
    }

    @Override
    public String getPressCode() throws Exception {
        String pressCode = sequenceUtils.getPressCode();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+pressCode;
    }

    @Override
    public String getTypeCode() throws Exception {
        String typeCode = sequenceUtils.getTypeCode();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+typeCode;
    }

    @Override
    public String getStyleCode() throws Exception {
        String styleCode = sequenceUtils.getStyleCode();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+styleCode;
    }

    @Override
    public String getUserId() throws Exception {
        return sequenceUtils.getUserId();
    }

    @Override
    public String getOrderId() throws Exception {
        String orderId = sequenceUtils.getOrderId();
        String pre = DateUtils.getSysdate(PRE_DATE_FORMAT);
        return pre+orderId;
    }
}
