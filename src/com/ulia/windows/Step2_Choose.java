package com.ulia.windows;

// импортируемые библиотеки

import com.ulia.classes.InformationSystem;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static com.ulia.functions.Components.paintButton;
import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Fields.*;

public class Step2_Choose extends JFrame {

    private JPanel contentPane; // панель содержимого
    private InformationSystem iSystem; // информационная система
    private JRadioButton radioButton1; // переключатель для первой кнопки
    private JRadioButton radioButton2; // переключатель для второй кнопки

    public Step2_Choose(InformationSystem informationSystem) {
        this.iSystem = informationSystem;
        setTitle("Выбор источника списка угроз");
        setBounds(100, 210, 1000, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // создаём группу кнопок, чтобы мы могли выбрать один из переключателей
        ButtonGroup buttonGroup = new ButtonGroup();
        radioButton1 = new JRadioButton("Классификация угроз безопасности персональных данных, обрабатываемых в информационных системах персональных данных");
        Components.paintButton(radioButton1, contentPane, buttonGroup, fontNormal, X_LEFT, 10, 980, 20);

        radioButton2 = new JRadioButton("Банк данных угроз безопасности информации");
        Components.paintButton(radioButton2, contentPane, buttonGroup, fontNormal, X_LEFT, 30, 980, 20);

        JButton buttonGoToStep = new JButton("Перейти ко выбору угроз персональных данных");
        paintComponent(buttonGoToStep, contentPane, fontBold, 560, 55, 400, 25);
        buttonGoToStep.addActionListener(actionEvent -> {
            String path = "";
            if (radioButton1.isSelected())
                path = "./res/com/ulia/threats.txt"; // если выбран первый переключаетель берём путь к файлу со списком угроз из методички по базовой модели
            else if (radioButton2.isSelected())
                path = "./res/com/ulia/thrlist.csv"; // если выбран второй - берём путь к файлу со списком угроз из общего банка
            // но если мы не выбрали ни одного переключателя, будет всплывающее окно с предупреждением
            if (path.isEmpty())
                JOptionPane.showMessageDialog(null, "Вы не выбрали источник списка угроз", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else {
                new Step2_Select(iSystem, path); // переходим к следующему окну, передавая эземпляр класса информационной системы (объект) и путь к фалу
                setVisible(false);
            }
        });
        setVisible(true);
    }
}