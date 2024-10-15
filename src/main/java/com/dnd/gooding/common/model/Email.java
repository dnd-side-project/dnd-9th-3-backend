package com.dnd.gooding.common.model;

import lombok.Getter;

@Getter
public class Email {

    private String address;

    public Email() {}

    public Email(String address) {
        this.address = address;
    }
}
