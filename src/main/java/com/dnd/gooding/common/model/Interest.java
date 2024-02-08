package com.dnd.gooding.common.model;

public class Interest {

    private String interestCode;
    private String interestName;

    public Interest() {}

    public Interest(String interestCode, String interestName) {
        this.interestCode = interestCode;
        this.interestName = interestName;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public String getInterestName() {
        return interestName;
    }
}
