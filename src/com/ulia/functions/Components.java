package com.ulia.functions;

import javax.swing.*;
import java.awt.*;

/**
 * Дополнительные функции
 *
 * @author ulia
 * @version 1.0
 */
public final class Components {

    /**
     * Метод добавления компонента в панель текущего фрейма.
     * <p>
     * Универсальный метод, с помощью которого можно добавить в выбранную панель contentPane любой компонент.
     * При чём указанный параметр contentPane должен быть обязательно наследником класса JPanel, а
     * параметр component - обязательно наследником класса Component
     *
     * @param component   подключаемый компонент
     * @param contentPane панель, куда кладём компонент
     * @param x           координата компонента
     * @param y           координата компонента
     * @param width       ширина компонента
     * @param height      высота компонента
     */

    public static <T extends Component, P extends JPanel> void paintComponent(T component, P contentPane, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height); // задаём положение и размеры компонента
        contentPane.add(component); // добавляем компонент в панель
    }

    /**
     * Метод добавления отключенного компонента в панель текущего фрейма (содержимое компонента нельзя редактировать)
     * <p>
     * Универсальный метод, с помощью которого можно добавить в выбранную панель contentPane любой компонент.
     * При чём указанный параметр contentPane должен быть обязательно наследником класса JPanel, а
     * параметр component - обязательно наследником класса Component
     *
     * @param component   подключаемый компонент
     * @param contentPane контейнер, куда кладём компонент
     * @param font        шрифт текста
     * @param x           координата компонента
     * @param y           координата компонента
     * @param width       ширина компонента
     * @param height      высота компонента
     */
    public static <T extends Component, P extends JPanel> void paintDisableComponent(T component, P contentPane, Font font, int x, int y, int width, int height) {
        component.setFont(font); // задаём для компонента выбранный шрифт
        component.setEnabled(false); // делаем недоступным компонент для редактирования
        component.setBounds(x, y, width, height); // задаём положение и размеры компонента
        component.setBackground(Color.BLACK); // задаём чёрный фон компонента
        contentPane.add(component); // добавляем компонент в панель
    }

    /**
     * Метод добавления компонента в панель текущего фрейма
     * <p>
     * Универсальный метод, с помощью которого можно добавить в выбранную панель contentPane любой компонент.
     * При чём указанный параметр contentPane должен быть обязательно наследником класса JPanel, а
     * параметр component - обязательно наследником класса Component
     *
     * @param component   подключаемый компонент
     * @param contentPane панель, куда кладём компонент
     * @param font        шрифт текста
     * @param x           координата компонента
     * @param y           координата компонента
     * @param width       ширина компонента
     * @param height      высота компонента
     */
    public static <T extends Component, P extends JPanel> void paintComponent(T component, P contentPane, Font font, int x, int y, int width, int height) {
        component.setFont(font); // задаём для компонента выбранный шрифт
        component.setBounds(x, y, width, height);
        contentPane.add(component); // добавляем компонент в панель
    }

    /**
     * Метод добавления компонента в панель текущего фрейма
     * <p>
     * Универсальный метод, с помощью которого можно добавить в выбранную панель contentPane любой компонент.
     * При чём указанный параметр contentPane должен быть обязательно наследником класса JPanel, а
     * параметр component - обязательно наследником класса AbstractButton. То есть компонент должен пренадлежать к типу кнопок.
     * Для связки всех кнопок в общую группу добавлен параметр buttonGroup.
     *
     * @param component   подключаемый компонент типа кнопки (JButton, JRadioButton и т.д.)
     * @param contentPane панель, куда кладём компонент
     * @param buttonGroup группа кнопок, куда добавляем текущий компонент
     * @param font        шрифт текста
     * @param x           координата компонента
     * @param y           координата компонента
     * @param width       ширина компонента
     * @param height      высота компонента
     */
    public static <T extends AbstractButton, P extends JPanel> void paintButton(T component, P contentPane, ButtonGroup buttonGroup, Font font, int x, int y, int width, int height) {
        component.setFont(font); // задаём для компонента выбранный шрифт
        component.setBounds(x, y, width, height); // задаём положение и размеры компонента
        contentPane.add(component); // добавляем компонент в панель
        buttonGroup.add(component); // добавлем комопнент в общую группу кнопок
    }

    /**
     * Метод добавления компонента в панель текущего фрейма
     * <p>
     * Универсальный метод, с помощью которого можно добавить в выбранную панель contentPane любой компонент.
     * При чём указанный параметр contentPane должен быть обязательно наследником класса JPanel, а
     * параметр component - обязательно наследником класса Component
     * параметр component - обязательно наследником класса AbstractButton. То есть компонент должен пренадлежать к типу кнопок.
     * Для связки всех кнопок в общую группу добавлен параметр buttonGroup.
     *
     * @param component   подключаемый компонент типа кнопки (JButton, JRadioButton и т.д.)
     * @param contentPane панель, куда кладём компонент
     * @param buttonGroup группа кнопок, куда добавляем текущий компонент
     * @param font        шрифт текста
     */
    public static <T extends AbstractButton, P extends JPanel> void paintButton(T component, P contentPane, ButtonGroup buttonGroup, Font font) {
        component.setFont(font); // задаём для компонента выбранный шрифт
        contentPane.add(component); // добавляем компонент в панель
        buttonGroup.add(component); // добавлем комопнент в общую группу кнопок
    }
}