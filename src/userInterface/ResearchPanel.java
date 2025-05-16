package userInterface;

import controller.ResearchesController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ResearchPanel extends JPanel {
    private String [] researches = {"Boissons alcoolisées", "Produit en dessous du seuil", "Informations commande d'un produit"};
    private JComboBox research;
    private ResearchesController controller;
    private JTable table;
    private JScrollPane scrollPane;
    private AlcoholicDrinksModel model;


    public ResearchPanel() {

        research = new JComboBox(researches);
        research.addActionListener(new researchListener());
        research.setSelectedIndex(-1);
        this.add(research);
        //model = new AlcoholicDrinksModel(controller.getAlcDrinksBeforeDate());
        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(50);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 800));
        //this.add(scrollPane);
        this.setVisible(true);
    }

    private class researchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String)research.getSelectedItem();
            if (selectedItem.equals("Boissons alcoolisées")) {
                JSpinner spinner = new JSpinner();
                add(spinner);
            }
        }
    }
}
