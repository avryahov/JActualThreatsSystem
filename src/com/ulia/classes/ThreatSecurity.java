package com.ulia.classes;

import com.ulia.enums.DangerThreat;
import com.ulia.enums.Possibility;
import com.ulia.enums.ReleaseThreat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Класс угрозы безопасности персональных данных информационной системы
public class ThreatSecurity {

    private String view; // вид угрозы
    private String name; // наименование угрозы
    private Possibility possibility; // вероятность возникновения
    private ReleaseThreat releaseThreat; // реализация угрозы
    private DangerThreat dangerThreat; // опасноть угрозы
    private boolean actual; // актуальность угрозы

    // Конструктор класса с параметрами (вид угрозы, наименование угрозы)
    public ThreatSecurity(String view, String name) {
        this.view = view;
        this.name = name;
        this.possibility = Possibility.EMPTY; // по умолчанию при создании экземпляра класса вероятность угрозы будет пустой
        this.dangerThreat = DangerThreat.EMPTY; // по умолчанию при создании экземпляра класса опасноть угрозы будет пустой
        this.actual = false; // по умолчанию при создании экземпляра класса актуальность угрозы будет неактуальной
    }

    // Геттер для вида угрозы
    public String getView() {
        return view;
    }

    /*// Сеттер для вида угрозы
    public void setView(String view) {
        this.view = view;
    }*/

    // Геттер для наименования угрозы
    public String getName() {
        return name;
    }

    /*// Сеттер для наименования угрозы
    public void setName(String name) {
        this.name = name;
    }*/

    // Геттер для вероятности угрозы
    public Possibility getPossibility() {
        return possibility;
    }

    // Сеттер для вероятности угрозы
    public void setPossibility(Possibility possibility) {
        this.possibility = possibility;
    }

    // Геттер для реализации угрозы
    public ReleaseThreat getReleaseThreat() {
        return releaseThreat;
    }

    // Сеттер для реализации угрозы
    public void setReleaseThreat(ReleaseThreat releaseThreat) {
        this.releaseThreat = releaseThreat;
    }

    // Геттер для опасности угрозы
    public DangerThreat getDangerThreat() {
        return dangerThreat;
    }

    // Сеттер для опасности угрозы
    public void setDangerThreat(DangerThreat dangerThreat) {
        this.dangerThreat = dangerThreat;
    }

    // Является ли угроза актуальной или нет
    public boolean isActual() {
        return actual;
    }

    // Сеттер для актуальности угрозы
    public void setActual(boolean actual) {
        this.actual = actual;
    }

    // Функция инициазиации списка угроз, а точнее чтение списка угроз из текстового файла
    // path - путь к файлу
    // возвращаемое значение - список объектов угроз
    public static ArrayList<ThreatSecurity> initial(String path) {
        ArrayList<ThreatSecurity> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(";");
                if (array.length >= 2) {
                    String viewThreat = array[0].trim();
                    String nameThreat = array[1].trim();
                    ThreatSecurity threatSecurity = new ThreatSecurity(viewThreat, nameThreat);
                    if (!list.contains(threatSecurity)) list.add(threatSecurity);
                }
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage()); // вывод в консоль сообщения об ошибке
            e.printStackTrace(); // вывод в консоль стека ошибок (последовательность возникновения ошибки)
        }
        return list;
    }

}