package com.ulia.enums;

// Перечисление реализации угрозы
public enum ReleaseThreat {

    LOW("низкая реализация угрозы", 0),

    MEDIUM("средняя реализация угрозы", 1),

    HIGH("высокая реализация угрозы", 2),

    VERY_HIGH("очень высокая реализация угрозы", 3);

    private String name; // наименование реализации угрозы
    private int mask; // маска реализации угрозы в виде номера

    // Конструктор для перечисления с параметрами - наименование реализации угрозы, маски
    ReleaseThreat(String name, int mask) {
        this.name = name;
        this.mask = mask;
    }

    // Геттер для наименования реализации угрозы
    public String getName() {
        return name;
    }

    // Геттер для маски реализации угрозы
    public int getMask() {
        return mask;
    }
}
