package com.ulia.classes;

import com.ulia.enums.CategoryData;

import java.util.ArrayList;

public class PersonalData {

    private int sortOrder;
    private String name;
    private CategoryData category;

    public PersonalData(int sortOrder, String name, CategoryData category) {
        this.sortOrder = sortOrder;
        this.name = name;
        this.category = category;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getName() {
        return name;
    }

    public CategoryData getCategory() {
        return category;
    }

    public static ArrayList<PersonalData> init() {
        ArrayList<PersonalData> list = new ArrayList<>();
        list.add(new PersonalData(1, "фамилия", CategoryData.PUBLIC));
        list.add(new PersonalData(2, "имя", CategoryData.PUBLIC));
        list.add(new PersonalData(3, "отчество", CategoryData.PUBLIC));
        list.add(new PersonalData(4, "дата рождения", CategoryData.PUBLIC));
        list.add(new PersonalData(5, "место рождения", CategoryData.PUBLIC));
        list.add(new PersonalData(6, "адрес проживания", CategoryData.PUBLIC));
        list.add(new PersonalData(7, "паспортные данные", CategoryData.PUBLIC));
        list.add(new PersonalData(8, "семейное положение", CategoryData.PUBLIC));
        list.add(new PersonalData(9, "телефонный номер", CategoryData.PUBLIC));
        list.add(new PersonalData(10, "имущественное положение", CategoryData.PUBLIC));
        list.add(new PersonalData(11, "социальное положение", CategoryData.PUBLIC));
        list.add(new PersonalData(12, "профессия", CategoryData.PUBLIC));
        list.add(new PersonalData(13, "образование", CategoryData.PUBLIC));
        list.add(new PersonalData(14, "доходы", CategoryData.PUBLIC));
        list.add(new PersonalData(15, "информация о трудовой деятельности", CategoryData.PUBLIC));
        list.add(new PersonalData(16, "информация о трудовом стаже", CategoryData.PUBLIC));
        list.add(new PersonalData(17, "состояние здоровья", CategoryData.SPECIAL));
        list.add(new PersonalData(18, "состояние интимной жизни", CategoryData.SPECIAL));
        list.add(new PersonalData(19, "философские убеждения", CategoryData.SPECIAL));
        list.add(new PersonalData(20, "политические взгляды", CategoryData.SPECIAL));
        list.add(new PersonalData(21, "религиозные убеждения", CategoryData.SPECIAL));
        list.add(new PersonalData(22, "национальная принадлежность", CategoryData.SPECIAL));
        list.add(new PersonalData(23, "расовая принадлежность", CategoryData.SPECIAL));
        list.add(new PersonalData(24, "данные о наградах", CategoryData.OTHER));
        list.add(new PersonalData(25, "данные о медалях", CategoryData.OTHER));
        list.add(new PersonalData(26, "данные о поощрениях", CategoryData.OTHER));
        list.add(new PersonalData(27, "данные о почетных званиях", CategoryData.OTHER));
        list.add(new PersonalData(28, "информация о знании иностранных языков", CategoryData.OTHER));
        list.add(new PersonalData(29, "форма допуска", CategoryData.OTHER));
        list.add(new PersonalData(30, "биометрически данные", CategoryData.BIOMETRIC));
        return list;
    }
}
