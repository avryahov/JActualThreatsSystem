package com.ulia.windows;

import com.ulia.classes.InformationSystem;
import com.ulia.classes.Panel;
import com.ulia.classes.ThreatSecurity;
import com.ulia.enums.Possibility;
import com.ulia.enums.ReleaseThreat;
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

public class Step3 extends JFrame {

    private Panel contentPane;
    private Stream<Possibility> possibilityStream;
    private InformationSystem informationSystem;
    private ArrayList<ThreatSecurity> selectedThreats;
    private String selectedName;
    private Panel panelThreats;
    private JScrollPane scrollPane;
    private int size = 0;

    public Step3(InformationSystem system, ArrayList<ThreatSecurity> threats) {

        this.selectedThreats = threats;
        this.informationSystem = system;
        size = selectedThreats.size();

        setTitle("Частота реализации угроз");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // метод для указания операции при закрытии окна, т.е. закрытии приложения в целом

        Dimension screenSize = getToolkit().getScreenSize(); // Получения размера текущего экрана компьютера
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration()); // Получения размера текущей панели задач компьютера
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) (screenSize.getHeight() - screenInsets.top);

        contentPane = new Panel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        Stream<Possibility> stream = Arrays.stream(Possibility.values());
        List<String> possibilityNames = stream.map(v -> v.getName()).collect(Collectors.toList());

        if (size > 15) {
            panelThreats = new Panel();
            panelThreats.setLayout(null);
            panelThreats.setPreferredSize(new Dimension(screenWidth, size * 40 + 30));
        }

        int y = 10;
        List<int[]> arrayLines = new ArrayList<>();
        for (ThreatSecurity threatSecurity : selectedThreats) {
            int t = 1;
            String name = "<html>";
            String[] strings = threatSecurity.getName().split(" ");
            for (int i = 0; i < strings.length; i++) {
                if (i > 1 && i % 15 == 0) {
                    name += "<br />";
                    t++;
                }
                name += " " + strings[i];
            }
            JLabel label = new JLabel(name + "</html>");
            paintComponent(label, size > 15 ? panelThreats : contentPane, fontNormal, X_LEFT, y, screenWidth - 260, 20 * t);

            JComboBox comboBox = new JComboBox(possibilityNames.toArray());
            Components.paintComponent(comboBox, size > 15 ? panelThreats : contentPane, size > 15 ? screenWidth - 270 : screenWidth - 230, y, 210, 20);
            comboBox.addActionListener(actionEvent -> {
                JComboBox jComboBox = (JComboBox) actionEvent.getSource();
                selectedName = (String) jComboBox.getSelectedItem();
                possibilityStream = Arrays.stream(Possibility.values());
                Possibility possibility = possibilityStream.filter(f -> f.getName().equals(selectedName)).findFirst().orElse(Possibility.EMPTY);
                if (possibility != Possibility.EMPTY) threatSecurity.setPossibility(possibility);
            });
            y += 20 * t;

            arrayLines.add(new int[]{0, y + 15, screenWidth, y + 15});
            y += 20;
        }

        // Если угроз больше 15, то формируем скролиннг для панель угроз и добавляем его в панель содержимого
        if (size > 15) {
            panelThreats.drawLines(arrayLines);
            scrollPane = new JScrollPane(panelThreats, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            Components.paintComponent(scrollPane, contentPane, X_LEFT, 10, screenWidth - 25, screenHeight - 130);
        } else contentPane.drawLines(arrayLines);

        JButton buttonCalculate = new JButton("Вычислить частоту вероятности каждой угрозы");
        paintComponent(buttonCalculate, contentPane, fontBold, screenWidth - 400, (size > 15 ? screenHeight - 100 : y + 20), 380, 25);
        buttonCalculate.addActionListener(actionEvent -> {
            // Если в списке выбранных угроз хотя бы одной не проставленой вероятность возникновения угрозы, то возникнет всплывающее окно с предупреждением
            if (selectedThreats.stream().anyMatch(f -> f.getPossibility() == Possibility.EMPTY))
                JOptionPane.showMessageDialog(null, "Вы не выбрали степень вероятности угрозы", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            else {
                for (ThreatSecurity threatSecurity : selectedThreats) {
                    // получаем значение Y, исходя из формулы в методичке
                    double Y = ((double) (threatSecurity.getPossibility().getNumber() + informationSystem.getY1())) / 20D;
                    // И затем исходя из той же методички по набору условий определяем возможность реализации той или иной угрозы
                    if (0 <= Y && Y <= 0.3D) threatSecurity.setReleaseThreat(ReleaseThreat.LOW);
                    else if (0.3D < Y && Y <= 0.6D) threatSecurity.setReleaseThreat(ReleaseThreat.MEDIUM);
                    else if (0.6D < Y && Y <= 0.8D) threatSecurity.setReleaseThreat(ReleaseThreat.HIGH);
                    else if (Y > 0.8D) threatSecurity.setReleaseThreat(ReleaseThreat.VERY_HIGH);
                }
                new Step3_Result(informationSystem, selectedThreats);
                setVisible(false);
            }
        });

        // вычислем и задаём размер всего окна: если угроз больше 15, то высота окна равна высоте экрана, иначе в зависимости от последней координаты отрисованной выше последней в списке угрозы мы фиксируем высоту окна
        // ширина окна у нас равна ширине экрана
        setSize(screenWidth, size > 15 ? screenHeight : y + 100); // указание размеров окна таким же как размер дисплея
        setVisible(true);
    }
}