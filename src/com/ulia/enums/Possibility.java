package com.ulia.enums;

// Перечисление вероятностей возникновения угрозы (частоты)
public enum Possibility {

    EMPTY(null, ""), // пусто

    UNLIKELY(0, "маловероятно"), // маловероятно

    LOW(2, "низкая вероятность"), // низкая вероятность

    MEDIUM(5, "средняя вероятность"), // средняя вероятность

    HIGH(10, "высокая вероятность"); // высокая вероятность

    private Integer number; // числовой коэффицент вероятности возникновения угрозы
    private String name; // наменование вероятности возникновения угрозы

    // Конструктор перечисления с параметрами (числовой коэфицент, наименование)
    Possibility(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    // Геттер для числового коэффицента вероятности возникновения угрозы
    public Integer getNumber() {
        return number;
    }

    // Геттер для наименования вероятности возникновения угрозы
    public String getName() {
        return name;
    }
}
