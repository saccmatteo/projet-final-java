package userInterface;

import controller.AlcoholicDrinkController;

import javax.swing.*;
import java.time.LocalDate;

public class ResearchPanel extends JPanel {
    AlcoholicDrinkController controller;
    JTable table;
    JScrollPane scrollPane;
    AlcoholicDrinksModel model;

    public ResearchPanel() {
        setController(new AlcoholicDrinkController());
        LocalDate date = LocalDate.of(2001, 1, 1);
        model = new AlcoholicDrinksModel(controller.getAlcDrinksBeforeDate(date));
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        this.setVisible(true);
    }

    public void setController(AlcoholicDrinkController controller) {
        this.controller = controller;
    }
}
