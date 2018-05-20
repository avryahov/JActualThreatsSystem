package com.ulia.classes;

import com.ulia.enums.AmountData;

import java.util.ArrayList;

// класс Информационная система
public class InformationSystem {

    private String name; // название информационной системы
    private int y1; // уровень исходной защищённости
    private ArrayList<PersonalData> dataArrayList; // список персональных данных
    private boolean accessOperator; // доступ оператора к испдн
    private AmountData amountData;

    public InformationSystem(){

    }

    // Конструктор класса
    public InformationSystem(String name, ArrayList<PersonalData> list, boolean access, AmountData amountData) {
        this.name = name;
        this.dataArrayList = list;
        this.accessOperator = access;
        this.amountData = amountData;
    }

    // Метод для извлечения названия системы (геттер)
    public String getName() {
        return name;
    }

    // Метод для задания названия системы (сеттер)
    public void setName(String name) {
        this.name = name;
    }

    // Геттер для уровня исходной защищённости
    public int getY1() {
        return y1;
    }

    // Сеттер для уровня исходной защищённости
    public void setY1(int y1) {
        this.y1 = y1;
    }

    public ArrayList<PersonalData> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<PersonalData> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public boolean isAccessOperator() {
        return accessOperator;
    }

    public void setAccessOperator(boolean accessOperator) {
        this.accessOperator = accessOperator;
    }

    public AmountData getAmountData() {
        return amountData;
    }

    public void setAmountData(AmountData amountData) {
        this.amountData = amountData;
    }
}
