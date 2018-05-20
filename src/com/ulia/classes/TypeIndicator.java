package com.ulia.classes;

// Класс типа показателя уровня защищённости
public class TypeIndicator {

    private int sortOrder; // порядок сортировки типа
    private String name; // наименование типа

    // Конструктор класса
    public TypeIndicator(int sortOrder, String name) {
        this.sortOrder = sortOrder;
        this.name = name;
    }

    // Геттер для порядка сортировки
    public int getSortOrder() {
        return this.sortOrder;
    }

    /*// Сеттер для порядка сортировки
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }*/

    // Геттер для наименование типа
    public String getName() {
        return this.name;
    }

    /*// Сеттер для наименование типа
    public void setName(String type) {
        this.type = type;
    }*/

}
