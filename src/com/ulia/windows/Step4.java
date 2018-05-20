package com.ulia.windows;

import com.ulia.classes.InformationSystem;
import com.ulia.classes.Panel;
import com.ulia.classes.ThreatSecurity;
import com.ulia.enums.DangerThreat;
import com.ulia.functions.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ulia.functions.Components.paintComponent;
import static com.ulia.functions.Fields.*;

public class Step4 extends JFrame {

    private Panel contentPane; // наша главная панель содержимого окна
    private InformationSystem informationSystem; // информационная система
    private ArrayList<ThreatSecurity> selectedThreats; // список экзмепляров или объектов угроз
    private Stream<DangerThreat> dangerStream; // поток списка опасности угроз
    private String selectedName; // выбранное имя (опасность из выпадающего списка)
    private Panel panelThreats; // панель для угроз
    private JScrollPane scrollPane; // скролиннг для панели угроз
    private int size = 0; // количество выбранных угроз

    public Step4(InformationSystem system, ArrayList<ThreatSecurity> threats) {

        this.selectedThreats = threats;
        this.informationSystem = system;
        size = selectedThreats.size(); // получаем и запоминаем количество выбранных угроз

        setTitle("Опасность реализации угроз");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом

        Dimension screenSize = getToolkit().getScreenSize(); // Получения размера текущего экрана компьютера
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration()); // Получения размера текущей панели задач компьютера
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) (screenSize.getHeight() - screenInsets.bottom);

        contentPane = new Panel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        Stream<DangerThreat> stream = Arrays.stream(DangerThreat.values());
        List<String> dangerNames = stream.map(v -> v.getName()).collect(Collectors.toList());

        // если количество больше 15 до формируем панель для угроз
        if (size > 15) {
            panelThreats = new Panel();
            panelThreats.setLayout(null);
            panelThreats.setPreferredSize(new Dimension(screenWidth, size * 40 + 30));
        }

        int y = 10;
        List<int[]> arrayLines = new ArrayList<>();
        for (ThreatSecurity threatSecurity : selectedThreats) {
            // Ниже представлен цикл по переносу слов в строке
            int t = 1;
            String name = "<html>"; // задаём открытый тег, будто у нас веб-страница
            String[] strings = threatSecurity.getName().split(" "); // с помощью "сплита" формирую массив строк без пробелов
            for (int i = 0; i < strings.length; i++) {
                if (i > 1 && i % 15 == 0) { // если индекс больше ноля, т.е со второго слова идём, и 15 слов в одной строчке
                    name += "<br />"; // делаем перевод строки
                    t++;
                }
                name += " " + strings[i];
            }
            JLabel label = new JLabel(name + "</html>");
            paintComponent(label, size > 15 ? panelThreats : contentPane, fontNormal, X_LEFT, y, screenWidth - 300, 20 * t);

            JComboBox comboBox = new JComboBox(dangerNames.toArray());
            paintComponent(comboBox, size > 15 ? panelThreats : contentPane, fontNormal, screenWidth - 270, y, 210, 20);
            comboBox.addActionListener(actionEvent -> {
                JComboBox jComboBox = (JComboBox) actionEvent.getSource();
                selectedName = (String) jComboBox.getSelectedItem();
                dangerStream = Arrays.stream(DangerThreat.values());
                DangerThreat dangerThreat = dangerStream.filter(f -> f.getName().equals(selectedName)).findFirst().orElse(DangerThreat.EMPTY);
                if (dangerThreat != DangerThreat.EMPTY) threatSecurity.setDangerThreat(dangerThreat);
            });
            y += 20 * t;

            arrayLines.add(new int[]{0, y + 15, screenWidth, y + 15});
            y += 20;
        }

        // Если угроз больше 15, то формируем скролиннг для панель угроз и добавляем его в панель содержимого
        if (size > 15) {
            panelThreats.drawLines(arrayLines);
            // скролинг для панели со списком угроз, разрешаем только вертикальную прокрутку, а горизонтальную запрещаем
            scrollPane = new JScrollPane(panelThreats, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            Components.paintComponent(scrollPane, contentPane, X_LEFT, 10, screenWidth - 25, screenHeight - 150);
        } else contentPane.drawLines(arrayLines);

        // Ниже добавляем кнопку для вычисления актуальности каждой угрозы и перехода в окно результатов
        JButton buttonCalculate = new JButton("Вычислить актуальность каждой угрозы");
        paintComponent(buttonCalculate, contentPane, fontBold, screenWidth - 400, (size > 15 ? screenHeight - 110 : y + 20), 380, 25);
        buttonCalculate.addActionListener(actionEvent -> {
            // если хоть где-нибудь опасноть для угрозы не выбрана, то появляется всплывающее окно с предупреждением
            if (selectedThreats.stream().anyMatch(f -> f.getDangerThreat() == DangerThreat.EMPTY))
                JOptionPane.showMessageDialog(null, "Вы не выбрали опасность угрозы", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else {
                for (ThreatSecurity threatSecurity : selectedThreats) {
                    int maskD = threatSecurity.getDangerThreat().getMask(); // получаем маску опасности угрозы
                    int maskR = threatSecurity.getReleaseThreat().getMask(); // получаем маску вероятности реализации угрозы
                    boolean actual = false;
                    // здесь нужно смотреть на основе таблицы и пересечения масок вместо наименование опасностей и вероятностей реализации
                    if (maskD == 1 && maskR == 1)
                        actual = true; // например, если опасноть средняя и вероятность реализации средняя, то угроза актуальная
                    else if (maskD == 0 && maskR > 1)
                        actual = true; // иначе если опастноть низкая, но вероятность реализации выше среднего, то угроза актуальна
                    else if (maskR == 0 && maskD > 1)
                        actual = true; // иначе если вероятность реализации низкая, а опасность выше среднего, то угроза актуальна
                    else if (maskD > 1)
                        actual = true; // в противном случаем проверяем, что если опасноть выше среднего - угроза актуальна
                    else if (maskR > 1)
                        actual = true; // а также проверяем, что если вероятность реализации выше среднего - угроза актуальна

                    //если угроза нигде не прошла выше проверку, то по умолчанию она неакутальна
                    threatSecurity.setActual(actual);
                }
                new Step4_Result(informationSystem, selectedThreats);
                setVisible(false);
            }
        });

        // вычислем и задаём размер всего окна: если угроз больше 15, то высота окна равна высоте экрана, иначе в зависимости от последней координаты отрисованной выше последней в списке угрозы мы фиксируем высоту окна
        // ширина окна у нас равна ширине экрана
        setSize(screenWidth, size > 15 ? screenHeight : y + 100); // указание размеров окна таким же как размер дисплея
        setVisible(true);
    }
}