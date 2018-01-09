package com.nuist.bookmMarket.model;

public class Cust {
    private Integer custId;

    private String custName;

    private Integer password;

    private Integer custRight;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Integer getCustRight() {
        return custRight;
    }

    public void setCustRight(Integer custRight) {
        this.custRight = custRight;
    }
}