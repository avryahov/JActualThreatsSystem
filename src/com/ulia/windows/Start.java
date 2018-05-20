package com.ulia.windows;

// импортируемые библиотеки

import com.ulia.classes.InformationSystem;
import com.ulia.classes.PersonalData;
import com.ulia.enums.AmountData;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;

// статический импорт библиотек (чтобы не использовать при вызове метода название класса)

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Fields.*;

// Класс "Старт", наследуемый от класса JFrame
public class Start extends JFrame {

    private JPanel contentPane; // панель содержимого (контента)
    private JTextField textField; // текстовое поле
    private ArrayList<PersonalData> dataArrayList; // список выбранных персональных данных
    private ArrayList<PersonalData> selectedData; // список выбранных персональных данных
    private JRadioButton radioButtonOperatorT; // переключатель оператора с доступом
    private JRadioButton radioButtonOperatorF; // переключатель оператора без доступа
    private Integer value; // значение слайдера
    private int width;

    // Конструктор класса
    public Start() {
        setTitle("Система определения актуальности угроз ИСПДн"); // метод для указания заголовка окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом
        setBounds(200, 100, 600, 570); // указания координат местоположения и размеров окна
        setResizable(false);

        this.width = 575;

        contentPane = new JPanel(); // инициализация панели
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // задание типа рамки панели (безрамочной с указанием )
        setContentPane(contentPane); // метод позволяет заменить панель содержимого окна.
        contentPane.setLayout(null); // задание макета (шаблона) панели (абсолютный или пустой - размещение компонентов по своему усмотрению)

        JLabel jLabelName = new JLabel("Введите название информационной системы:"); // создание метки
        paintComponent(jLabelName, contentPane, fontBold, X_LEFT, 12, width, 15); // отрисовка компонента на панели текушего фрейма

        textField = new JTextField();
        Components.paintComponent(textField, contentPane, X_LEFT, 40, width, 20);

        JLabel jLabelSelect = new JLabel("Выберете персональные данные, используемые в системе:"); // создание метки
        paintComponent(jLabelSelect, contentPane, fontBold, X_LEFT, 65, width, 15);

        dataArrayList = PersonalData.init(); // получение списка всего перечня персональных данных
        selectedData = new ArrayList<>();

        JPanel panelData = new JPanel();
        panelData.setLayout(null);
        panelData.setPreferredSize(new Dimension(350, dataArrayList.size() * 20));

        int y = 2;
        dataArrayList.sort(Comparator.comparing(PersonalData::getSortOrder)); // сортировка списка персональных данных по номеру
        for (PersonalData data : dataArrayList) {
            JCheckBox checkBox = new JCheckBox(data.getName()); // создаём новый check для проставления "галочки"
            paintComponent(checkBox, panelData, fontNormal, 2, y, width, 15);
            checkBox.addActionListener(e -> {
                JCheckBox item = (JCheckBox) e.getSource();

                // Получаем наменование персональных данных
                String name = item.getText().trim();
                // Форматированное имя сверяем с общим списком, чтобы найти совпадение; в противном случае возвращаем пустое значение
                PersonalData currentData = dataArrayList.stream().filter(f -> f.getName().contains(name)).findFirst().orElse(null);
                if (currentData != null) {
                    // если значение не пусто, проверяем были ли выбраны персональные данные
                    if (item.isSelected()) {
                        if (!selectedData.contains(currentData)) // если в списке выбранных данных их нет, то мы добавляем в список
                            selectedData.add(currentData);
                    } else {
                        if (selectedData.contains(currentData)) // иначе если мы данные не выбрали (а может отменили выбор), то проверяем их в списке выбранных
                            selectedData.remove(currentData); // если они есть в списке, то их удалеем из перечня
                    }
                }
            });
            y += 20;
        }

        // добавляем на панель скроллинг, внутрь которого добавляем внутреннюю панель, куда добавили все "чеки"
        // при этом задали два параметы о необходимости вертикальной прокрутки и запрете горизонтальной
        JScrollPane scrollPane = new JScrollPane(panelData, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(X_LEFT, 90, width, 250);
        contentPane.add(scrollPane);

        JLabel jLabelOperator = new JLabel("Выберете имеет ли оператор доступ к информационной системе или нет:"); // создание метки
        paintComponent(jLabelOperator, contentPane, fontBold, X_LEFT, 350, width, 15);

        ButtonGroup buttonGroup = new ButtonGroup(); // задаём группу кнопок
        radioButtonOperatorT = new JRadioButton("оператор имеет доступ к испдн");
        Components.paintButton(radioButtonOperatorT, contentPane, buttonGroup, fontNormal, X_LEFT, 370, 255, 20);

        radioButtonOperatorF = new JRadioButton("оператор не имеет доступ к испдн");
        Components.paintButton(radioButtonOperatorF, contentPane, buttonGroup, fontNormal, X_LEFT + 255, 370, 280, 20);

        JLabel jLabelAmount = new JLabel("Выберете объем персональных данных, обрабатываемых в испдн:"); // создание метки
        paintComponent(jLabelAmount, contentPane, fontBold, X_LEFT, 405, width, 15);

        // создаём слайдер (ползунок), при этом указываем, что у нас будет три значения по шкале (0, 1, 2), где 0 - минимальное, а 2 - максимальное значение
        JSlider slider = new JSlider(0, 2, 0);

        // создаём список-словарь, где каждая пара представленна в виде ключ-значение
        // при этом у нас пара имеет конкретные типы для ключа и значения
        // добавляем трижды в список пары с указанием "номера веса", текстового обозначения (дополнительно форматируя его)
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        labels.put(new Integer(AmountData.FIRST.getNumber()), new JLabel("<html><font color=blue size=3>более чем 100 000</font></html>"));
        labels.put(new Integer(AmountData.SECOND.getNumber()), new JLabel("<html><font color=gray size=3>от 1000 до 100 000</font></html>"));
        labels.put(new Integer(AmountData.THIRD.getNumber()), new JLabel("<html><font color=red size=3>менее чем 1000</font></html>"));

        slider.setLabelTable(labels); // добавляем в слайдер набор из словаря
        slider.setPaintLabels(true); // говорим, что их нужно отрисовать (отобразить)
        slider.setMajorTickSpacing(50); // устанавливаем максимальный интервал тиков (расстояния между значениями по шкале)
        slider.setBounds(X_LEFT, 430, 576, 30); // задаём месторасположение и размеры слайдера

        value = new Integer(0); // задаём, что начальное значение равно нулю с помощшью класса-обёртки (т.е. по умолчанию у нас вес более чем 100 000 субъектов)
        slider.addChangeListener(e -> value = ((JSlider) e.getSource()).getValue()); // обрабатываем событие перемещения ползунка, сохраняя выбранное значение в переменную
        contentPane.add(slider); // добавляем слайдер на панель

        JButton buttonSaveAndStart = new JButton("Сохранить данные и перейти к первому этапу"); // инициализация кнопки
        paintComponent(buttonSaveAndStart, contentPane, fontBold, 198, 490, 390, 25);

        // добавления слушателя для кнопки (если она совершит действие)
        buttonSaveAndStart.addActionListener(actionEvent -> { // обработчик события
            String name = textField.getText().trim(); // берём текст и удаляем лишние пробелы по обеим стороная строки
            // если название информационной системы пусто - выводим диалоговое окно с предупреждением
            if (name.isEmpty())
                JOptionPane.showMessageDialog(null, "Вы не указали название информационной системы", "Ошибка", JOptionPane.ERROR_MESSAGE);
            else if (selectedData.size() == 0)
                JOptionPane.showMessageDialog(null, "Вы не выбрали персональные данные информационной системы", "Ошибка", JOptionPane.ERROR_MESSAGE);
            else if (!radioButtonOperatorT.isSelected() && !radioButtonOperatorF.isSelected())
                JOptionPane.showMessageDialog(null, "Вы не указали, имеет ли доступ оператор к информационной системы или нет", "Ошибка", JOptionPane.ERROR_MESSAGE);
            else if (value == null)
                JOptionPane.showMessageDialog(null, "Вы не выбрали объем персональных данных, обрабатываемых в информационной системе", "Ошибка", JOptionPane.ERROR_MESSAGE);
            else {
                AmountData amountData = AmountData.values()[value.intValue()];
                // иначе создаём экземпляр класса информационной системы и кладём в поле класса значение названия, список выбранных персональных данных, причастие оператора к доступу, объём данных
                InformationSystem iSystem = new InformationSystem(name, selectedData, radioButtonOperatorT.isSelected(), amountData);
                new Step1(iSystem); // переходим ко второму окну (первому этапу) и передаём экземпляр класса информационный системы
                setVisible(false); // делаем стартовое текущее окно невидимым
            }
        });
        setVisible(true); // делаем стартовое текущее окно видимым
    }

}