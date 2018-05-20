package com.ulia.windows;

import com.ulia.classes.InformationSystem;
import com.ulia.classes.Panel;
import com.ulia.classes.ThreatSecurity;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Fields.*;

public class Step2_Select extends JFrame {

    private JPanel contentPane;
    private InformationSystem iSystem;
    private ArrayList<ThreatSecurity> threatSecurities;
    private ArrayList<ThreatSecurity> selectedThreatSecurities = new ArrayList<>();
    private Panel panelThreats;

    public Step2_Select(InformationSystem informationSystem, String path) {
        this.iSystem = informationSystem;

        setTitle("Угрозы безопасности персональных данных информационной системы");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом
        setResizable(false);

        Dimension screenSize = getToolkit().getScreenSize(); // Получения размера текущего экрана компьютера
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration()); // Получения размера текущей панели задач компьютера
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) (screenSize.getHeight() - screenInsets.bottom);
        setSize(screenWidth, screenHeight); // указание размеров окна таким же как размер дисплея

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        try {

            threatSecurities = ThreatSecurity.initial(path);
            Stream<ThreatSecurity> stream = threatSecurities.stream();
            Map<String, List<ThreatSecurity>> types = stream.collect(Collectors.groupingBy(ThreatSecurity::getView));
            int max = threatSecurities.stream().max(Comparator.comparing(t -> t.getName().length())).get().getName().length();

            panelThreats = new Panel();
            panelThreats.setLayout(null);
            panelThreats.setPreferredSize(new Dimension(max * 8, threatSecurities.size() * 15 + types.size() * 45));

            int y = 0;
            List<int[]> arrayLines = new ArrayList<>(); // задаём список из набора массивов, где каждый набор - это массив из чётырёх цифр (координат начала и окончания отрисовки линии)
            for (Map.Entry<String, List<ThreatSecurity>> item : types.entrySet()) {

                List<ThreatSecurity> threatsByView = item.getValue();

                JLabel labelView = new JLabel(item.getKey() + ":");
                paintComponent(labelView, panelThreats, fontBold, X_LEFT, y, screenWidth, 30);
                y += 30;

                // Сортировка показателей по их порядковому номеру
                threatsByView.sort(Comparator.comparing(ThreatSecurity::getName));
                for (ThreatSecurity threatSecurity : threatsByView) {

                    // Создаём элемент для проставления галочки напротив угрозы
                    JCheckBox checkBox = new JCheckBox(threatSecurity.getName());
                    paintComponent(checkBox, panelThreats, fontNormal, X_LEFT, y, max * 8, 15);
                    y += 15;

                    checkBox.addActionListener(actionEvent -> {
                        JCheckBox item1 = (JCheckBox) actionEvent.getSource();

                        // Получаем наменование угрозы, предварительно заменив теги в тексте на пустые значения
                        String name1 = item1.getText().trim();//.replaceAll("<html>", "").replaceAll("</html>", "").replaceAll("<br />", "").trim();
                        // Форматированное имя сверяем с общим списком, чтобы найти совпадение; в противном случае возвращаем пустое значение
                        ThreatSecurity currentThreat = threatSecurities.stream().filter(f -> f.getName().contains(name1)).findFirst().orElse(null);
                        if (currentThreat != null) {
                            // если значение не пусто, проверяем была ли выбрана текущая угроза
                            if (item1.isSelected()) {
                                if (!selectedThreatSecurities.contains(currentThreat)) // если в списке выбранных угроз её нет, то мы добавляем в список
                                    selectedThreatSecurities.add(currentThreat);
                            } else {
                                if (selectedThreatSecurities.contains(currentThreat)) // иначе если мы угрозу не выбрали (а может отменили выбор), то проверяем её в списке выбранных
                                    selectedThreatSecurities.remove(currentThreat); // если она есть в списке, то её удалеем из перечня
                            }
                        }
                    });
                }

                // если набор угроз имеется, но добавляем в список координаты линии в виде массима из четырёх цифровых элементов
                if (threatsByView.size() > 0) {
                    arrayLines.add(new int[]{0, y + 15, screenWidth, y + 15});
                    y += 15;
                }
            }
            if (types.size() > 1) panelThreats.drawLines(arrayLines); // если групп больше одной отрисовываем все линии, разделяющие группы угроз

            // скролинг для панели со списком угроз, разрешаем только вертикальную прокрутку, а горизонтальную запрещаем
            JScrollPane scrollPane = new JScrollPane(panelThreats, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            Components.paintComponent(scrollPane, contentPane, X_LEFT, 10, screenWidth - 30, screenHeight - 110);

            JButton buttonGoNext = new JButton("Перейти к выбору вероятности угрозы");
            paintComponent(buttonGoNext, contentPane, fontBold, screenWidth - 370, screenHeight - 90, 350, 25);
            buttonGoNext.addActionListener(actionEvent -> {
                // если не одна угроза из общего списка не выбрана, то возникает всплывающее окно с предупреждением
                if (selectedThreatSecurities.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Вы ни одной угрозы из списка не выбрали", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                } else {
                    new Step3(iSystem, selectedThreatSecurities);
                    setVisible(false);
                }
            });
        } catch (Exception exp) {
            System.out.println(exp.getMessage()); // выводим в консоль собщение об ошибки, если она есть и мы её поймали
        }
        setVisible(true);
    }
}