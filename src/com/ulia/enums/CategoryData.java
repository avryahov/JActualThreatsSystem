package com.ulia.enums;

public enum CategoryData {

    PUBLIC("общедоступные"),

    SPECIAL("специальнные"),

    BIOMETRIC("биометрические"),

    OTHER("иные");

    private String name; // название категории

    CategoryData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
