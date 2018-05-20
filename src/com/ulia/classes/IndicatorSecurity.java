package com.ulia.classes;

import com.ulia.enums.SecurityLevel;

import java.util.ArrayList;

// Класс показателя уровня защищённости
public class IndicatorSecurity {

    // для справки: приватные поля класса отражает первый принцип ООП - инкапсуляция
    private TypeIndicator typeIndicator; // тип показателя
    private int sortOrder; // порядок сортировки показателя
    private String indicator; // показатель
    private SecurityLevel level; // уровень

    // Конструктор класса с параметрами (типа, порядка сортировки, показателя, уровня защищённости)
    public IndicatorSecurity(TypeIndicator typeIndicator, int sortOrder, String indicator, SecurityLevel level) {
        this.typeIndicator = typeIndicator;
        this.sortOrder = sortOrder;
        this.indicator = indicator;
        this.level = level;
    }

    // Метод для получения типа показателя (геттер - getter)
    public TypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    /*// Метод для передачи типа показателя (сеттер - setter)
    public void setTypeIndicator(TypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }
    */

    // Геттер для порядка сортировки
    public int getSortOrder() {
        return this.sortOrder;
    }

    /*// Сеттер для порядка сортировки
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }*/

    // Геттер для показателя
    public String getIndicator() {
        return this.indicator;
    }

    /*// Сеттер для показателя
    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }*/

    // Геттер для уровня защищённости
    public SecurityLevel getLevel() {
        return this.level;
    }

    /*// Сеттер для уровня защищённости
    public void setLevel(SecurityLevel level) {
        this.level = level;
    }*/

    // Геттер для порядка сортировки типа указателя
    public Integer getSortOrderType() {
        TypeIndicator typeIndicator = this.typeIndicator;
        return typeIndicator != null ? typeIndicator.getSortOrder() : 0;
    }

    // Метод инициализации класса (создание списка показателей с уровнями защищённости по методичке)
    public static ArrayList<IndicatorSecurity> initial() {
        ArrayList<IndicatorSecurity> list = new ArrayList<IndicatorSecurity>();

        TypeIndicator typeIndicator1 = new TypeIndicator(1, "По территориальному размещению");
        list.add(new IndicatorSecurity(typeIndicator1, 1, "распределенная ИСПДн, которая охватывает несколько областей, краев, округов или государство в целом", SecurityLevel.LOW));
        list.add(new IndicatorSecurity(typeIndicator1, 2, "городская ИСПДн, охватывающая не более одного населенного пункта (города, поселка)", SecurityLevel.LOW));
        list.add(new IndicatorSecurity(typeIndicator1, 3, "корпоративная распределенная ИСПДн, охватывающая многие подразделения одной организации", SecurityLevel.MEDIUM));
        list.add(new IndicatorSecurity(typeIndicator1, 4, "локальная (кампусная) ИСПДн, развернутая в пределах нескольких близко расположенных зданий", SecurityLevel.MEDIUM));
        list.add(new IndicatorSecurity(typeIndicator1, 5, "локальная ИСПДн, развернутая в пределах одного здания", SecurityLevel.HIGH));

        TypeIndicator typeIndicator2 = new TypeIndicator(2, "По наличию соединения с сетями общего пользования");
        list.add(new IndicatorSecurity(typeIndicator2, 1, "ИСПДн, имеющая многоточечный выход в сеть общего пользования", SecurityLevel.LOW));
        list.add(new IndicatorSecurity(typeIndicator2, 2, "ИСПДн, имеющая одноточечный выход в сеть общего пользования", SecurityLevel.MEDIUM));
        list.add(new IndicatorSecurity(typeIndicator2, 3, "ИСПДн, физически отделенная от сети общего пользования ", SecurityLevel.HIGH));

        TypeIndicator typeIndicator3 = new TypeIndicator(3, "По встроенным (легальным) операциям с записями баз персональных данных");
        list.add(new IndicatorSecurity(typeIndicator3, 1, "чтение, поиск", SecurityLevel.HIGH));
        list.add(new IndicatorSecurity(typeIndicator3, 2, "запись, удаление, сортировка", SecurityLevel.MEDIUM));
        list.add(new IndicatorSecurity(typeIndicator3, 3, "модификация, передача", SecurityLevel.LOW));

        TypeIndicator typeIndicator4 = new TypeIndicator(4, "По разграничению доступа к персональным данным");
        list.add(new IndicatorSecurity(typeIndicator4, 1, "ИСПДн, к которой имеют доступ определенные перечнем сотрудники организации, являющейся владельцем ИСПДн, либо субъект ПДн", SecurityLevel.MEDIUM));
        list.add(new IndicatorSecurity(typeIndicator4, 2, "ИСПДн, к которой имеют доступ все сотрудники организации, являющейся владельцем ИСПДн", SecurityLevel.LOW));
        list.add(new IndicatorSecurity(typeIndicator4, 3, "ИСПДн с открытым доступом", SecurityLevel.LOW));

        TypeIndicator typeIndicator5 = new TypeIndicator(5, "По наличию соединений с другими базами ПДн иных ИСПДн");
        list.add(new IndicatorSecurity(typeIndicator5, 1, "интегрированная ИСПДн (организация использует несколько баз ПДн ИСПДн, при этом организация не является владельцем всех используемых баз ПДн)", SecurityLevel.LOW));
        list.add(new IndicatorSecurity(typeIndicator5, 2, "ИСПДн,в которой используется одна база ПДн, принадлежащая организации – владельцу данной ИСПДн", SecurityLevel.HIGH));

        TypeIndicator typeIndicator6 = new TypeIndicator(6, "По уровню обобщения (обезличивания) ПДн");
        list.add(new IndicatorSecurity(typeIndicator6, 1, "ИСПДн, в которой предоставляемые пользователю данные являются обезличенными (на уровне организации, отрасли, области, региона и т.д.)", SecurityLevel.HIGH));
        list.add(new IndicatorSecurity(typeIndicator6, 2, "ИСПДн, в которой данные обезличиваются только при передаче в другие организации и не обезличены при предоставлении пользователю в организации", SecurityLevel.HIGH));
        list.add(new IndicatorSecurity(typeIndicator6, 3, "ИСПДн, в которой предоставляемые пользователю данные не являются обезличенными (т.е. присутствует информация, позволяющая идентифицировать субъекта ПДн)", SecurityLevel.LOW));

        TypeIndicator typeIndicator7 = new TypeIndicator(7, "По объему ПДн, которые предоставляются сторонним пользователям ИСПДн без предварительной обработки");
        list.add(new IndicatorSecurity(typeIndicator7, 1, "ИСПДн, предоставляющая всю базу данных с ПДн", SecurityLevel.LOW));
        list.add(new IndicatorSecurity(typeIndicator7, 2, "ИСПДн, предоставляющая часть ПДн", SecurityLevel.MEDIUM));
        list.add(new IndicatorSecurity(typeIndicator7, 3, "ИСПДн, не предоставляющая никакой информации", SecurityLevel.HIGH));

        return list;
    }

}
