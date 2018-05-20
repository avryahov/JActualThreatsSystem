package com.ulia.enums;

public enum AmountData {

    FIRST("более чем 100 000 субъектов"),

    //SECOND("от 1000 до 100 000 субъектов", 1),

    THIRD("менее чем 1000 субъектов");

    private String name;

    AmountData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
