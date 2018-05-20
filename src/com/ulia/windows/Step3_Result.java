package com.ulia.windows;

import com.ulia.classes.InformationSystem;
import com.ulia.classes.Panel;
import com.ulia.classes.ThreatSecurity;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Components.paintDisableComponent;
import static com.ulia.functions.Fields.*;

public class Step3_Result extends JFrame {

    private Panel contentPane;
    private InformationSystem informationSystem;
    private ArrayList<ThreatSecurity> selectedThreats;
    private Panel panelThreats;
    private JScrollPane scrollPane;
    private int size = 0;

    public Step3_Result(InformationSystem system, ArrayList<ThreatSecurity> threats) {

        this.selectedThreats = threats;
        this.informationSystem = system;
        size = selectedThreats.size();

        setTitle("Результат вычисления частоты реализации угроз");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом

        Dimension screenSize = getToolkit().getScreenSize(); // Получения размера текущего экрана компьютера
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration()); // Получения размера текущей панели задач компьютера
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) (screenSize.getHeight() - screenInsets.bottom);

        contentPane = new Panel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        if (informationSystem != null) {
            JLabel labelName = new JLabel("Информационная система персональных данных:");
            paintComponent(labelName, contentPane, fontBold, X_LEFT, 10, 500, 15);

            JTextArea jTextArea = new JTextArea(" " + informationSystem.getName());
            paintDisableComponent(jTextArea, contentPane, fontNormal, X_LEFT, 30, screenWidth - 24, 30);
            jTextArea.setLineWrap(true);
        }

        JLabel labelHead = new JLabel("Выбранные экспертом угрозы персональных данных системы:");
        paintComponent(labelHead, contentPane, fontBold, X_LEFT,65, 600, 15);

        if (size > 13) {
            panelThreats = new Panel();
            panelThreats.setLayout(null);
            panelThreats.setPreferredSize(new Dimension(screenWidth, size * 40 + 30));
        }

        int y = size > 13 ? 5 : 85;
        List<int[]> arrayLines = new ArrayList<>();
        for (ThreatSecurity threatSecurity : this.selectedThreats) {
            // Ниже представлен цикл по переносу слов в строке
            int t = 1;
            String name = "<html>"; // задаём открытый тег, будто у нас веб-страница
            String[] strings = threatSecurity.getName().split(" "); // с помощью "сплита" формирую массив строк без пробелов
            for (int i = 0; i < strings.length; i++) {
                if (i > 1 && i % 15 == 0) { // если индекс больше ноля, т.е со второго слова идём, и 10 слов в одной строчке
                    name += "<br />"; // делаем перевод строки
                    t++;
                }
                name += " " + strings[i];
            }

            JLabel labelThreat = new JLabel(name + "</html>");
            paintComponent(labelThreat, size > 13 ? panelThreats : contentPane, fontNormal, X_LEFT, y, screenWidth - 320, 20 * t);

            JTextField labelRelease = new JTextField(" " + threatSecurity.getReleaseThreat().getName());
            paintDisableComponent(labelRelease, size > 13 ? panelThreats : contentPane, fontNormal, screenWidth - 300, y, 250, 20);
            y += 20 * t;

            arrayLines.add(new int[]{0, y + 15, screenWidth, y + 15});
            y += 20;
        }

        // Если угроз больше или равно 15, то формируем скролиннг для панель угроз и добавляем его в панель содержимого
        if (size > 13) {
            panelThreats.drawLines(arrayLines);
            scrollPane = new JScrollPane(panelThreats, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            Components.paintComponent(scrollPane, contentPane, X_LEFT, 90, screenWidth - 24, screenHeight - 200);
        }
        else contentPane.drawLines(arrayLines);

        JButton buttonGoNext = new JButton("Перейти к выбору опасности каждой угрозы");
        paintComponent(buttonGoNext, contentPane, fontBold, screenWidth - 382, (size > 13 ? screenHeight - 100 : y + 20), 370, 25);
        buttonGoNext.addActionListener(actionEvent -> {
            new Step4(informationSystem, selectedThreats);
            setVisible(false);
        });

        // вычислем и задаём размер всего окна: если угроз больше или равно 15, то высота окна равна высоте экрана, иначе в зависимости от последней координаты отрисованной выше последней в списке угрозы мы фиксируем высоту окна
        // ширина окна у нас равна ширине экрана
        setSize(screenWidth, size > 13 ? screenHeight : y + 100); // указание размеров окна таким же как размер дисплея
        setVisible(true);
    }
}