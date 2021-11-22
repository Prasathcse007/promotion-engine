package com.ecom.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


public enum Constants {
    FIXED("FIXED"),
    PERCENTAGE("PERCENTAGE"),
    SUCCESS("Success"),
    FAILED("Failed"),
    INDIVIDUAL("INDIVIDUAL");


    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
