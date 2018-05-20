package com.ulia.enums;

public enum AmountData {

    FIRST("более чем 100 000 субъектов", 0),

    SECOND("от 1000 до 100 000 субъектов", 1),

    THIRD("менее чем 1000 субъектов", 2);

    private String name;
    private int number;

    AmountData(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
