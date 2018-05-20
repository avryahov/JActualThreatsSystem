package com.ulia.windows;

import com.ulia.classes.InformationSystem;
import com.ulia.classes.Panel;
import com.ulia.classes.PersonalData;
import com.ulia.classes.ThreatSecurity;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Components.paintDisableComponent;
import static com.ulia.functions.Fields.*;

public class Step4_Result extends JFrame {

    private Panel contentPane; // наша главная панель содержимого окна
    private InformationSystem informationSystem; // информационная система
    private ArrayList<ThreatSecurity> selectedThreats; // список экзмепляров или объектов угроз
    private int width;

    // набор заголовков полей
    private Object[] columnNames = new String[]{"Возможность реализации угрозы",
            "Низкий показатель опасности угрозы",
            "Средний показатель опасности угрозы",
            "Высокий показатель опасности угрозы"};

    // набор данных для вставки внутрь таблицы
    private Object[][] arrayData = new String[][]{{"Низкая", "неактульная", "неактульная", "актульная"},
            {"Средняя", "неактульная", "актульная", "актульная"},
            {"Высокая", "актульная", "актульная", "актульная"},
            {"Очень высокая", "актульная", "актульная", "актульная"}};

    public Step4_Result(InformationSystem system, ArrayList<ThreatSecurity> threats) {

        this.selectedThreats = threats;
        this.informationSystem = system;

        setTitle("Результат вычисления актуальности угроз");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом

        Dimension screenSize = getToolkit().getScreenSize(); // Получения размера текущего экрана компьютера
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration()); // Получения размера текущей панели задач компьютера
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) (screenSize.getHeight() - screenInsets.bottom);
        setSize(screenWidth, screenHeight); // указание размеров окна таким же как размер дисплея
        width = screenWidth - 25;

        contentPane = new Panel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        if (informationSystem != null) {
            JLabel labelName = new JLabel("Информационная система персональных данных:");
            paintComponent(labelName, contentPane, fontBold, X_LEFT, 10, width, 15);

            JTextArea jTextArea = new JTextArea(" " + informationSystem.getName());
            paintDisableComponent(jTextArea, contentPane, fontNormal, X_LEFT, 30, width, 30);
            jTextArea.setLineWrap(true); // перевод текста
        }

        JLabel labelHead = new JLabel("Выбранные экспертом опасноть реализации угроз персональных данных системы:");
        paintComponent(labelHead, contentPane, fontBold, X_LEFT, 65, width, 15);

        Panel panelThreats = new Panel();
        panelThreats.setLayout(null);
        panelThreats.setPreferredSize(new Dimension(screenWidth, selectedThreats.size() * 40 + 30));

        int y = 10;
        List<int[]> arrayLines = new ArrayList<>();
        for (ThreatSecurity threatSecurity : selectedThreats) {

            // Ниже представлен цикл по переносу слов в строке
            int t = 1;
            String name = "<html>"; // задаём открытый тег, будто у нас веб-страница
            String[] strings = threatSecurity.getName().split(" "); // с помощью "сплита" формирую массив строк без пробелов
            for (int i = 0; i < strings.length; i++) {
                if (i > 1 && i % 12 == 0) { // если индекс больше ноля, т.е со второго слова идём, и 8 слов в одной строчке
                    name += "<br />"; // делаем перевод строки
                    t++;
                }
                name += " " + strings[i];
            }
            JLabel labelThreat = new JLabel(name + "</html>"); // добавляем полученный текст в поле с закрывающимся тегом
            paintComponent(labelThreat, panelThreats, fontNormal, X_LEFT, y, screenWidth - 370, 20 * t);

            // текстовое поле с информацией об опасности угрозы
            JTextField labelDanger = new JTextField(" " + threatSecurity.getDangerThreat().getName());
            paintDisableComponent(labelDanger, panelThreats, fontNormal, screenWidth - 370, y, 150, 20);

            // текстовое поле с информацией об актуальности угрозы
            JTextField labelActual = new JTextField(" " + (threatSecurity.isActual() ? "актуальная" : "неактуальная"));
            paintDisableComponent(labelActual, panelThreats, fontNormal, screenWidth - 200, y, 100, 20);
            y += 20 * t;

            arrayLines.add(new int[]{0, y + 15, screenWidth, y + 15});
            y += 20;
        }
        panelThreats.drawLines(arrayLines);

        // Добавляем в наше окно скролинг для панели со списком угроз, разрешаем только вертикальную прокрутку, а горизонтальную запрещаем
        JScrollPane scrollPane = new JScrollPane(panelThreats, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(X_LEFT, 90, width, 150);
        contentPane.add(scrollPane);

        JLabel labelHead2 = new JLabel("Выбранные пользователем персональные данные информационной системы:");
        paintComponent(labelHead2, contentPane, fontBold, X_LEFT, scrollPane.getY() + 165, 600, 15);

        List<PersonalData> dataList = informationSystem.getDataArrayList();
        dataList.sort(Comparator.comparing(PersonalData::getSortOrder));

        Panel panelData = new Panel();
        panelData.setLayout(null);
        panelData.setPreferredSize(new Dimension(screenWidth, dataList.size() * 40));

        y = 10;
        List<int[]> arrayLines2 = new ArrayList<>();
        for (PersonalData personalData : dataList) {

            JLabel labelData = new JLabel(personalData.getName());
            paintComponent(labelData, panelData, fontNormal, X_LEFT, y, screenWidth - 300, 20);

            // текстовое поле с информацией о категории персональных данных угрозы
            JTextField labelCategory = new JTextField(" " + personalData.getCategory().getName());
            paintDisableComponent(labelCategory, panelData, fontNormal, screenWidth - 300, y, 200, 20);
            y += 20;

            arrayLines2.add(new int[]{0, y + 15, screenWidth, y + 15});
            y += 20;
        }
        panelData.drawLines(arrayLines2);

        JScrollPane scrollPane2 = new JScrollPane(panelData, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Components.paintComponent(scrollPane2, contentPane, X_LEFT, labelHead2.getY() + 20, width, 150);

        JLabel labelAmount = new JLabel("Объём персональных данных, задействованных в информационной системе: " + informationSystem.getAmountData().getName());
        paintComponent(labelAmount, contentPane, fontBold, X_LEFT, scrollPane2.getY() + 165, width, 15);

        JLabel labelAccess = new JLabel("Доступ оператора к информационной системе - " + (informationSystem.isAccessOperator() ? "имеет" : "не имеет"));
        paintComponent(labelAccess, contentPane, fontBold, X_LEFT, labelAmount.getY() + 30, width, 15);

        JLabel labelHeadTable = new JLabel("Правила отнесения угрозы безопасности персональных данных к актуальной", SwingConstants.CENTER);
        paintComponent(labelHeadTable, contentPane, fontBold, X_LEFT, screenHeight - 250, width, 15);

        // Задаём панель в виде "бокса", т.е. все элементы будут у нас располагаться сверху вниз, как "чипсы прингалс"
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setBounds(X_LEFT, screenHeight - 230, width, 86);
        // создаём таблицу и кладём в неё данный и заголовки полей
        JTable table = new JTable(arrayData, columnNames);
        table.setFont(fontNormal);
        box.add(new JScrollPane(table)); // добавляем таблицу в скроллинг и кидаем её в наш "бокс"
        contentPane.add(box); // бокс соответственно добавляется в панель содержимого

        String level = "";
        switch (informationSystem.getY1()) {
            case 0:
                level = " высокая степень исходной защищённости системы";
                break;
            case 5:
                level = " средняя степень исходной защищённости системы";
                break;
            case 10:
                level = " низкая степень исходной защищённости системы";
                break;
        }
        JTextField jTextField = new JTextField(level);
        paintDisableComponent(jTextField, contentPane, fontNormal, X_LEFT, screenHeight - 120, width, 20);

        setVisible(true);
    }
}