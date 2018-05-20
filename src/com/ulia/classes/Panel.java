package com.ulia.classes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Класс панели, наследованный от общео JPanel
public class Panel extends JPanel {

    private List<int[]> arrayLines; // список линий

    // метод отрисовки линий (но мы просто здесь передаём в поле список созданных линий)
    public void drawLines(List<int[]> list) {
        this.arrayLines = list;
    }

    // переопределённый метод отрисовки компонента
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arrayLines != null) { // если список не пуст
            g.setColor(Color.BLACK); // задаём чёрный цвет для линий
            for (int[] array : arrayLines) {
                g.drawLine(array[0], array[1], array[2], array[3]); // с помощью цикла перебираем все координаты линии и отрисовываем их в нужной панели
            }
        }
    }
}
