package com.dnd.gooding.common.model;

import lombok.Getter;

@Getter
public class Interest {

    private String interestCode;
    private String interestName;

    public Interest() {}

    public Interest(String interestCode, String interestName) {
        this.interestCode = interestCode;
        this.interestName = interestName;
    }
}
