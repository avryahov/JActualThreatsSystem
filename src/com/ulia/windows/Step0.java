package com.ulia.windows;

// импортируемые библиотеки

import com.ulia.classes.InformationSystem;
import com.ulia.classes.PersonalData;
import com.ulia.enums.AmountData;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

// статический импорт библиотек (чтобы не использовать при вызове метода название класса)

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Fields.*;

// Класс "Старт", наследуемый от класса JFrame
public class Step0 {

    private JTextField textField; // текстовое поле
    private ArrayList<PersonalData> dataArrayList; // список выбранных персональных данных
    private ArrayList<PersonalData> selectedData; // список выбранных персональных данных
    private JCheckBox checkBoxOperatorT; // флажок оператора как сотрудника
    private JCheckBox checkBoxOperatorF; // флажок оператора как не сотрудника
    private JCheckBox checkBoxAmountT; // флажок объёма без доступа
    private JCheckBox checkBoxAmountF; // флажок объёма без доступа
    private int width;

    // Конструктор класса
    public Step0(JPanel contentPane) {

        this.width = contentPane.getWidth() - 30;

        JLabel jLabelName = new JLabel("Введите название информационной системы:"); // создание метки
        paintComponent(jLabelName, contentPane, fontBold, X_LEFT, 12, width, 15); // отрисовка компонента на панели текушего фрейма

        textField = new JTextField();
        Components.paintComponent(textField, contentPane, X_LEFT, 40, width, 20);

        JLabel jLabelSelect = new JLabel("Выберете персональные данные, используемые в системе:"); // создание метки
        paintComponent(jLabelSelect, contentPane, fontBold, X_LEFT, 65, width, 15);

        dataArrayList = PersonalData.init(); // получение списка всего перечня персональных данных
        selectedData = new ArrayList<>();

        int y = jLabelSelect.getY() + 25;
        int x = X_LEFT;
        dataArrayList.sort(Comparator.comparing(PersonalData::getSortOrder)); // сортировка списка персональных данных по номеру
        PersonalData dataMax = dataArrayList.stream().max(Comparator.comparingInt(f -> f.getName().length())).get();
        int widthCheckBox = dataMax.getName().length() * 8;
        for (PersonalData data : dataArrayList) {
            JCheckBox checkBox = new JCheckBox(data.getName()); // создаём новый check для проставления "галочки"
            int index = dataArrayList.indexOf(data);
            if (index > 1 && index % 8 == 0) {
                y = jLabelSelect.getY() + 25;
                x += widthCheckBox + 10;
                widthCheckBox += 10;
            }
            paintComponent(checkBox, contentPane, fontNormal, x, y, widthCheckBox, 15);

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
        y = jLabelSelect.getY() + 25 + 8 * 20;

        JLabel jLabelOperator = new JLabel("Выберете является ли оператор сотрудником в информационной системе или нет:"); // создание метки
        paintComponent(jLabelOperator, contentPane, fontBold, X_LEFT, y + 20, 600, 15);

        int operatorY = jLabelOperator.getY();

        ButtonGroup buttonGroup1 = new ButtonGroup(); // задаём группу кнопок
        checkBoxOperatorT = new JCheckBox("является сотрудником");
        Components.paintButton(checkBoxOperatorT, contentPane, buttonGroup1, fontNormal, X_LEFT, operatorY + 20, 255, 20);

        checkBoxOperatorF = new JCheckBox("не является сотрудником");
        Components.paintButton(checkBoxOperatorF, contentPane, buttonGroup1, fontNormal, X_LEFT + 260, operatorY + 20, 280, 20);

        JLabel jLabelAmount = new JLabel("Выберете объем персональных данных, обрабатываемых в испдн:"); // создание метки
        paintComponent(jLabelAmount, contentPane, fontBold, jLabelOperator.getX() + 610, operatorY, width, 15);

        ButtonGroup buttonGroup2 = new ButtonGroup(); // задаём группу кнопок
        checkBoxAmountT = new JCheckBox("более чем 100 000");
        Components.paintButton(checkBoxAmountT, contentPane, buttonGroup2, fontNormal, jLabelOperator.getX() + 610, operatorY + 20, 255, 20);

        checkBoxAmountF = new JCheckBox("менее чем 1000");
        Components.paintButton(checkBoxAmountF, contentPane, buttonGroup2, fontNormal, checkBoxAmountT.getX() + 260, operatorY + 20, 280, 20);

        JButton buttonSaveAndStart = new JButton("Сохранить основные данные информационной системы персональных данных"); // инициализация кнопки
        paintComponent(buttonSaveAndStart, contentPane, fontBold, X_LEFT, checkBoxAmountT.getY() + 50, width - 12, 25);

        // добавления слушателя для кнопки (если она совершит действие)
        buttonSaveAndStart.addActionListener(actionEvent -> { // обработчик события
            String name = textField.getText().trim(); // берём текст и удаляем лишние пробелы по обеим стороная строки
            // если название информационной системы пусто - выводим диалоговое окно с предупреждением
            if (name.isEmpty())
                JOptionPane.showMessageDialog(null, "Вы не указали название информационной системы", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else if (selectedData.size() == 0)
                JOptionPane.showMessageDialog(null, "Вы не выбрали персональные данные информационной системы", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else if (!checkBoxOperatorT.isSelected() && !checkBoxOperatorF.isSelected())
                JOptionPane.showMessageDialog(null, "Вы не указали, является ли оператор сотрудником или нет", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else if (!checkBoxAmountT.isSelected() && !checkBoxAmountF.isSelected())
                JOptionPane.showMessageDialog(null, "Вы не указали объём данных информационной системы", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else {
                AmountData amountData = AmountData.values()[checkBoxAmountT.isSelected() ? 0 : 1];
                // иначе создаём экземпляр класса информационной системы и кладём в поле класса значение названия, список выбранных персональных данных, причастие оператора к доступу, объём данных
                InformationSystem informationSystem = new InformationSystem(name, selectedData, checkBoxOperatorT.isSelected(), amountData);
                Frame.informationSystem.setName(name);
                Frame.informationSystem.setDataArrayList(selectedData);
                Frame.informationSystem.setAccessOperator(checkBoxOperatorT.isSelected());
                Frame.informationSystem.setAmountData(amountData);
                Frame.textSystemName.setText(" " + (informationSystem == null ? "" : informationSystem.getName()));
                List<PersonalData> list = selectedData;
                selectedData.sort(Comparator.comparing(f -> f.getSortOrder()));
                if (list == null) return;
                if (list.size() > 0) {
                    Object[][] dataObjects = new String[list.size()][];
                    int i = 0;
                    for (PersonalData data : list) {
                        dataObjects[i] = new String[]{data.getName(), data.getCategory().getName()};
                        i++;
                    }
                    Frame.tablePersonalData.setModel(new DefaultTableModel(dataObjects, new String[]{"Наименование ПДн", "Категория ПДн"}));
                }
                Frame.labelAccessResult.setText(checkBoxOperatorT.isSelected() ? "является" : "не является");
                Frame.labelAmountResult.setText(checkBoxAmountT.isSelected() ? checkBoxAmountF.getText() : checkBoxAmountF.getText());
            }
        });
    }
}