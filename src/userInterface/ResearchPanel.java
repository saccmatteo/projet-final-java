package userInterface;

import controller.AlcoholicDrinkController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class ResearchPanel extends JPanel {
    private AlcoholicDrinkController controller;
    private JTable table;
    private JScrollPane scrollPane;
    private AlcoholicDrinksModel model;

    public ResearchPanel() {
        setController(new AlcoholicDrinkController());
        LocalDate date = LocalDate.of(2001, 1, 1);
        model = new AlcoholicDrinksModel(controller.getAlcDrinksBeforeDate(date));
        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(50);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 800));
        this.add(scrollPane);
        this.setVisible(true);
    }

    public void setController(AlcoholicDrinkController controller) {
        this.controller = controller;
    }
}
