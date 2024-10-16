package com.dnd.gooding.common.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum InterestType {
    SHOPPING("1", "쇼핑"),
    TRAVEL("2", "여행"),
    DELICACIES("3", "미식"),
    READING("4", "독서"),
    COOKING("5", "요리"),
    CULTURE("6", "문화"),
    SPORTS("7", "스포츠"),
    HOBBY("8", "취미"),
    STUDY("9", "학업"),
    GOODING_DAY("10", "이달의 굳이데이");

    private String interestCode;
    private String interestName;

    InterestType(String interestCode, String interestName) {
        this.interestCode = interestCode;
        this.interestName = interestName;
    }

    public static InterestType of(String interestCode) {
        return Arrays.stream(InterestType.values())
                .filter(interest -> interest.getInterestCode().equals(interestCode))
                .findAny()
                .orElse(null);
    }
}
