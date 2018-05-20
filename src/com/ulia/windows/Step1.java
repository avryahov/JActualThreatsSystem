package com.ulia.windows;

// импортируемые библиотеки

import com.ulia.classes.IndicatorSecurity;
import com.ulia.classes.InformationSystem;
import com.ulia.enums.SecurityLevel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ulia.functions.Components.paintButton;
import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Fields.fontBold;
import static com.ulia.functions.Fields.fontNormal;

// Класс "Оценка уровня защищённости", наследуемый от класса JFrame
public class Step1 extends JFrame {

    private JPanel contentPane;
    private ArrayList<IndicatorSecurity> allIndicators; // все показателя в методичке в виде списка
    private String getName; // имя показателя
    private int sortOrderType; // порядок сортировки типа показателя
    private ArrayList<IndicatorSecurity> selectedIndicators = new ArrayList<>(); // выбранные пользователем показатели
    private InformationSystem iSystem;

    public Step1(InformationSystem informationSystem) {

        this.iSystem = informationSystem;

        setResizable(false); // указываем, что нельзя менять размер окна
        setTitle("Оценка уровня исходной защищённости"); // задаём заголовок
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = getToolkit().getScreenSize(); // Получения размера текущего экрана компьютера
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration()); // Получения размера текущей панели задач компьютера
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) (screenSize.getHeight() - screenInsets.top);

        setSize(screenWidth, screenHeight); // указание размеров окна таким же как размер дисплея

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("Выберите показатели, соответствующие вашей информационной системы:");
        paintComponent(label, contentPane, fontBold, 12, 12, 550, 15);

        // создаём конструкцию try-catch - обработчик ошибок, т.е. если вдруг ошибка внутри, программы будет работать, а мы в консоле прочтём её
        try {

            allIndicators = IndicatorSecurity.initial(); // получаем все показатели из класса
            Stream<IndicatorSecurity> indicatorSecurityStream = allIndicators.stream(); // создаём поток на основе списка
            // Группируем список по порядковому номеру типа показателя
            Map<Integer, List<IndicatorSecurity>> types = indicatorSecurityStream.collect(Collectors.groupingBy(IndicatorSecurity::getSortOrderType));

            int y = 40; // стартовая координата по оси y
            int t = 0; // счётчик
            int lastY = 0; // последняя координата по оси y
            // задаём цикл foreach для перебора всех элементов после группировки (сортировка по ключу (порядковому номеру) проходит автоматом)
            for (Map.Entry<Integer, List<IndicatorSecurity>> item : types.entrySet()) {

                sortOrderType = item.getKey().intValue(); // получение порядковой номера типа показателя
                String typeName = "";
                // Получение первого попапвшегося показателя, где порядковый номера типа совподает с общим списком
                IndicatorSecurity firstFindIndicator = allIndicators.stream().filter(f -> f.getSortOrderType() == sortOrderType).findFirst().orElse(null);
                // Если показатель не пуст, то получаем название типа показателя
                if (firstFindIndicator != null) typeName = firstFindIndicator.getTypeIndicator().getName();

                List<IndicatorSecurity> indicatorsByType = item.getValue(); // получаем список показателя текущего списка
                int size = indicatorsByType.size(); // получаем размер списка (количество показателей)

                lastY = y + (t * 90); // увеличиваем координату y

                JPanel panelIndicators = new JPanel(); // создаём панель
                // Задаём макет для панели в виде сетки количество_показателей на 1 (строки Х столбцы)
                panelIndicators.setLayout(new GridLayout(size, 1));
                // Задаём размеры текущей панели, где ширина панели будет всегда автоматически подстравивается под текущий размер экрана (- 100 пискелей)
                panelIndicators.setBounds(22, lastY, screenWidth - 50, 90);
                // Задаём рамку вокруг панели с указание наменования типа показателя и его порядкового номера
                panelIndicators.setBorder(BorderFactory.createTitledBorder(String.format("<html><font type=Times New Roman>%d. %s</font><html>", sortOrderType, typeName)));
                panelIndicators.setFont(fontNormal);
                contentPane.add(panelIndicators); // добавляем новую панель в родительскую (общую панель окна)
                t++;

                // Сортировка показателей по их порядковому номеру
                indicatorsByType.sort(Comparator.comparingInt(IndicatorSecurity::getSortOrder));
                ButtonGroup buttonGroup = new ButtonGroup(); // создание группы кнопок
                for (IndicatorSecurity indicator : indicatorsByType) { // перебираем поэлементо каждый показатель

                    JRadioButton radio = new JRadioButton(indicator.getIndicator()); // создаём переключатель с названием показателя
                    paintButton(radio, panelIndicators, buttonGroup, fontNormal); // отрисовка переключателя на панели

                    // задаём слушатель
                    radio.addActionListener(actionEvent -> { // задаём обработчик события, если к показателю обратятся (нажмут на него)
                        JRadioButton radioItem = (JRadioButton) actionEvent.getSource(); // получаем текущий переключатеь (выбранный)
                        getName = radioItem.getText(); // берём его название (название показателя)
                        // Если переключатель выбран (мы должны добавить его в список выбранных показателей)
                        if (radioItem.isSelected()) {
                            // Берём из отфильтрованного списка нужный экземпляр или пустой объект
                            IndicatorSecurity selectedIndicator = allIndicators.stream().filter(i -> i.getIndicator().equals(getName)).findFirst().orElse(null);
                            // Если внутри списка выбранных показателей такого ещё нет
                            if (selectedIndicators.stream().filter(si -> si.getIndicator().equals(getName)).collect(Collectors.toList()).size() == 0) {
                                // если экземпляр не пуст, то с помощью фильтра в потоке берём все показатели, совпадающие с порядковым номеро типа выбранного показателя, и удаляем их из списка
                                if (selectedIndicator != null) {
                                    List<IndicatorSecurity> removeIndicators = selectedIndicators.stream().filter(si -> si.getSortOrderType() == selectedIndicator.getSortOrderType() && si.getIndicator() != getName).collect(Collectors.toList());
                                    selectedIndicators.removeAll(removeIndicators);
                                }
                                selectedIndicators.add(selectedIndicator); // добавляем показатель в список выбранных
                            }
                        }
                    });
                }
            }

            JButton buttonCalculate = new JButton("Вычислить уровень исходной защищённости");
            paintComponent(buttonCalculate, contentPane, fontBold, screenWidth - 400, lastY + 100, 370, 25);

            buttonCalculate.addActionListener(actionEvent -> {
                int countTypes = allIndicators.stream().collect(Collectors.groupingBy(IndicatorSecurity::getTypeIndicator)).entrySet().size();

                // Если список выбранных показателей пуст, то вызываем диалоговое окно с предупреждением
                if (selectedIndicators.size() == 0)
                    JOptionPane.showMessageDialog(null, "Вы ни одного показателя из списка не выбрали", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                else if (selectedIndicators.size() != countTypes)
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать показатели всех типов", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    // Иначе выполняем необходимые действия по вычислению
                else {
                    // Общее количество показателей со средним уровнем защищёности
                    double commonCountM = (double) allIndicators.stream().filter(f -> f.getLevel() == SecurityLevel.MEDIUM).collect(Collectors.groupingBy(IndicatorSecurity::getTypeIndicator)).entrySet().size();
                    // Общее количество показателей с высоким уровнем защищёности
                    double commonCountH = (double) allIndicators.stream().filter(f -> f.getLevel() == SecurityLevel.HIGH).collect(Collectors.groupingBy(IndicatorSecurity::getTypeIndicator)).entrySet().size();

                    // Количество выбранных показателей с высоким уровнем защищённости
                    double countHIGH = (double) selectedIndicators.stream().filter(f -> f.getLevel() == SecurityLevel.HIGH).count();
                    // Количество выбранных показателей со средним уровнем защищённости
                    double countMEDIUM = (double) selectedIndicators.stream().filter(f -> f.getLevel() == SecurityLevel.MEDIUM).count();
                    // Количество выбранных показателей с низким уровнем защищённости
                    double countLOW = (double) selectedIndicators.stream().filter(f -> f.getLevel() == SecurityLevel.LOW).count();

                    int Y1;
                    double percentHIGH = countHIGH / (commonCountH / 100D); // процент выбранных показателей с высоким уровнем защищённости
                    double percentMEDIUM = countMEDIUM / ((commonCountM / 100D)); // процент выбранных показателей со средним уровнем защищённости

                    // Если процент с высоким уровнем выше 70%, имеются показатели со средним уровнем и не имеются с низким, то уровень исходной защищённости является высоким
                    if (percentHIGH >= 70D && percentMEDIUM >= 0D && countLOW == 0D) Y1 = 0;
                        // Иначе если процент с высоким уровнем ниже 70%, имеются показатели со средним уровнем не ниже 70% и имеют часть с низким, то уровень исходной защищённости является средним
                    else if (percentMEDIUM >= 70D) Y1 = 5;
                    else Y1 = 10; // в противном случае - низкий уровень исходной защищённости
                    iSystem.setY1(Y1);

                    new Step1_Result(iSystem, selectedIndicators);
                    setVisible(false);
                }
            });
        } catch (Exception exp) {
            System.out.println(exp.getMessage()); // выводим в консоль собщение об ошибке, если она есть и мы её поймали
        }
        setVisible(true);
    }
}