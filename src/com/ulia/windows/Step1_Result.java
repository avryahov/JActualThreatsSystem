package com.ulia.windows;

// импортируемые библиотеки

import com.ulia.classes.IndicatorSecurity;
import com.ulia.classes.InformationSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.Comparator;

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Components.paintDisableComponent;
import static com.ulia.functions.Fields.*;

public class Step1_Result extends JFrame {

    private JPanel contentPane;
    private InformationSystem iSystem;
    private static final int WIDTH = 776;

    public Step1_Result(InformationSystem informationSystem, ArrayList<IndicatorSecurity> list) {
        this.iSystem = informationSystem;
        setTitle("Результат вычисления уровня исходной защищённости");
        setBounds(100, 100, 800, 470);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        int Y1 = -1;
        if (informationSystem != null) {
            JLabel labelName = new JLabel("Информационная система персональных данных:");
            paintComponent(labelName, contentPane, fontBold, X_LEFT, 10, WIDTH, 15);

            JTextArea jTextArea = new JTextArea(" " + informationSystem.getName());
            paintDisableComponent(jTextArea, contentPane, fontNormal, X_LEFT, 30, WIDTH, 30);
            jTextArea.setLineWrap(true); // перевод текста

            Y1 = informationSystem.getY1();
        }

        JLabel labelHead = new JLabel("Выбранные показатели:");
        paintComponent(labelHead, contentPane, fontBold, X_LEFT, 65, WIDTH, 15);

        int y = 85;
        list.sort(Comparator.comparing(c -> c.getIndicator()));
        for (IndicatorSecurity indicatorSecurity : list) {
            JTextArea jTextArea = new JTextArea(" - " + indicatorSecurity.getIndicator());
            paintDisableComponent(jTextArea, contentPane, fontNormal, X_LEFT, y, WIDTH, 30);
            jTextArea.setLineWrap(true);
            y += 35;
        }

        JLabel labelLevel = new JLabel("Информационная система персональных данных:");
        paintComponent(labelLevel, contentPane, fontBold, X_LEFT, y + 10, WIDTH, 15);

        String level = "";
        switch (Y1) {
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
        paintDisableComponent(jTextField, contentPane, fontNormal, X_LEFT, y + 30, WIDTH, 20);

        JButton buttonGoToStep = new JButton("Перейти ко второму этапу");
        paintComponent(buttonGoToStep, contentPane, fontBold, 538, y + 60, 250, 25);

        buttonGoToStep.addActionListener(actionEvent -> {
            new Step2_Choose(iSystem);
            setVisible(false);
        });
        setVisible(true);
    }
}