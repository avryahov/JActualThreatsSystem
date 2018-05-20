package com.ulia.windows;

import javax.swing.*;
import java.awt.*;

import static com.ulia.functions.Components.paintComponent;
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

    /**инициализация контейнера*/ {
        mainPane = new JPanel();
        mainPane.setLayout(null);
    }

    /**инициализация по умолчанию контента панели*/ {
        contentPane = new JPanel();
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
        super("Актуальность угроз безопасности ИСПДн");
        Dimension screeSize = getToolkit().getScreenSize();
        setSize(screeSize.width, screeSize.height);
        setResizable(false);
        setContentPane(mainPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paintMenu();
        contentPane.setBounds(X_LEFT, X_LEFT + DEFAULT_HEIGHT_BUTTON + 10, button1.getWidth() + button2.getWidth() + button3.getWidth() + button4.getWidth() + button5.getWidth() + button6.getWidth() + 50, screeSize.height - X_LEFT - DEFAULT_HEIGHT_BUTTON - 10 - 300);
        mainPane.add(contentPane);
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

}
