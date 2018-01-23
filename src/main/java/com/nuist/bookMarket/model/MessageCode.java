package com.nuist.bookMarket.model;

import org.springframework.stereotype.Component;

@Component
public class MessageCode {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
