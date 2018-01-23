package com.nuist.bookMarket.service;

public interface MessageService {


    String sendTplSms(String mobile, String tplId, String content);

    String createCode();

    boolean validate(String code);

}
