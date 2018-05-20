package com.ulia.windows;

import com.ulia.classes.InformationSystem;
import com.ulia.classes.PersonalData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Components.paintDisableComponent;
import static com.ulia.functions.Fields.*;

public class Frame extends JFrame {

    private final JPanel mainPane;
    private JPanel contentPane;

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;

    private final int COMMON_WIDTH;

    public final static InformationSystem informationSystem = new InformationSystem();
    public final static JTextArea textSystemName = new JTextArea();
    public static JTable tablePersonalData;
    public static JLabel labelAmountResult;
    public static JLabel labelAccessResult;
    public static JLabel labelYResult;

    /**инициализация контейнера*/ {
        mainPane = new JPanel();
        mainPane.setLayout(null);
    }

    /**инициализация по умолчанию контента панели*/ {
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GRAY);
    }

    /**инициализация кнопок горизонтального меню*/ {
        button1 = new JButton("Ввод данных");
        button2 = new JButton("<html><center>Определение уровня<br />исходной защищённости ИСПДн</center></html>");
        button3 = new JButton("<html><center>Определение частоты<br />реализации угроз безопасности ПДн</center></html>");
        button4 = new JButton("<html><center>Определение опасности<br />угроз безопасности ПДн</center></html>");
        button5 = new JButton("<html><center>Определение коэффициента<br />реализуемости угроз<br />безопасности ПДн</center></html>");
        button6 = new JButton("<html><center>Формирование перечня<br />актуальных угроз</center></html>");

    }

    public Frame() {
        super("Актуальность угроз безопасности информационной системы безопасности данных");
        Dimension screeSize = getToolkit().getScreenSize();
        setSize(screeSize.width, screeSize.height);
        setResizable(false);
        setContentPane(mainPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        paintMenu();
        activeMenuListeners();

        COMMON_WIDTH = button1.getWidth() + button2.getWidth() + button3.getWidth() + button4.getWidth() + button5.getWidth() + button6.getWidth() + 50;

        contentPane.setBounds(X_LEFT, X_LEFT + DEFAULT_HEIGHT_BUTTON + 10, COMMON_WIDTH, screeSize.height - X_LEFT - DEFAULT_HEIGHT_BUTTON - 10 - 300);
        mainPane.add(contentPane);

        int bottom_y = contentPane.getY() + contentPane.getHeight() + 10;

        JLabel labelName = new JLabel("Информационная система персональных данных:");
        paintComponent(labelName, mainPane, fontBold, X_LEFT, bottom_y, 360, 30);

        paintDisableComponent(textSystemName, mainPane, fontNormal, X_LEFT + labelName.getWidth() + 10, bottom_y, COMMON_WIDTH - labelName.getWidth() - 10, 30);
        textSystemName.setLineWrap(true); // перевод текста

        tablePersonalData = new JTable(new Object[][]{{"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}}, new String[]{"Наименование ПДн", "Категория ПДн"});
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setBounds(X_LEFT, labelName.getY() + 50, 600, 150);
        tablePersonalData.setFont(fontNormal);
        box.add(new JScrollPane(tablePersonalData)); // добавляем таблицу в скроллинг и кидаем её в наш "бокс"
        mainPane.add(box); // бокс соответственно добавляется в панель содержимого

        JLabel labelAmount = new JLabel("Объём персональных данных, задействованных в ИСПДн - ");
        paintComponent(labelAmount, mainPane, fontBold, X_LEFT + 650, labelName.getY() + 50, 600, 15);
        labelAmountResult = new JLabel("_______________");
        paintDisableComponent(labelAmountResult, mainPane, fontBold, COMMON_WIDTH - 150, labelName.getY() + 50, 150, 15);

        JLabel labelAccess = new JLabel("Оператор информационной системы ПДн - ");
        paintComponent(labelAccess, mainPane, fontBold, X_LEFT + 650, labelAmount.getY() + 30, 600, 15);
        labelAccessResult = new JLabel("_______________");
        paintDisableComponent(labelAccessResult, mainPane, fontBold, COMMON_WIDTH - 150, labelAmount.getY() + 30, 150, 15);

        JLabel labelY = new JLabel("Уровень исходной защищённости - ");
        paintComponent(labelY, mainPane, fontBold, X_LEFT + 650, labelAccess.getY() + 30, 600, 15);
        labelYResult = new JLabel("_______________");
        paintDisableComponent(labelYResult, mainPane, fontBold, COMMON_WIDTH - 150, labelAccess.getY() + 30, 150, 15);


        setVisible(true);
    }

    private void paintMenu() {
        paintComponent(button1, mainPane, fontBold, X_LEFT, X_LEFT, 140, DEFAULT_HEIGHT_BUTTON);
        paintComponent(button2, mainPane, fontBold, button1.getX() + 150, X_LEFT, DEFAULT_WIDTH_BUTTON, DEFAULT_HEIGHT_BUTTON);
        paintComponent(button3, mainPane, fontBold, button2.getX() + DEFAULT_WIDTH_BUTTON + 10, X_LEFT, DEFAULT_WIDTH_BUTTON, DEFAULT_HEIGHT_BUTTON);
        paintComponent(button4, mainPane, fontBold, button3.getX() + DEFAULT_WIDTH_BUTTON + 10, X_LEFT, DEFAULT_WIDTH_BUTTON, DEFAULT_HEIGHT_BUTTON);
        paintComponent(button5, mainPane, fontBold, button4.getX() + DEFAULT_WIDTH_BUTTON + 10, X_LEFT, DEFAULT_WIDTH_BUTTON + 15, DEFAULT_HEIGHT_BUTTON);
        paintComponent(button6, mainPane, fontBold, button5.getX() + DEFAULT_WIDTH_BUTTON + 25, X_LEFT, DEFAULT_WIDTH_BUTTON - 20, DEFAULT_HEIGHT_BUTTON);
    }

    private void activeMenuListeners() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Step0(contentPane);
                contentPane.setBackground(Color.WHITE);

            }
        });
    }

}
