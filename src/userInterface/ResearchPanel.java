package userInterface;

import controller.ProductController;
import controller.ResearchesController;
import model.Product;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Supplier;

public class ResearchPanel extends JPanel {
    private String [] researches = {"Boissons alcoolisées", "Produit en dessous du seuil", "Informations commande d'un produit"};
    private JComboBox researchComboBox;
    private ResearchesController researchesController;
    private ProductController productController;
    private JTable table;
    private JButton researchButton;
    private JScrollPane scrollPane;
    private AlcoholicDrinksModel alcoholicDrinksModel;
    private ProductsUnderThresholdModel productsUnderThresholdModel;
    private JSpinner daySpinner, monthSpinner, yearSpinner, tresholdSpinner;
    private OrderInfosModel orderInfosModel;
    private JList<Product> productJList;
    private JPanel researchPanel, tablePanel, dynamicPanel;

    public ResearchPanel() {
        setLayout(new BorderLayout());
        setResearchesController(new ResearchesController());
        setProductController(new ProductController());
        researchPanel = new JPanel();
        dynamicPanel = new JPanel();
        tablePanel = new JPanel();
        researchComboBox = new JComboBox(researches);
        researchComboBox.addActionListener(new researchListener());
        researchComboBox.setSelectedIndex(-1);
        researchPanel.add(researchComboBox);
        researchButton = new JButton("Effectuer la recherche");
        researchButton.addActionListener(new ResearchButtonListener());
        researchPanel.add(researchButton);
        this.add(researchPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(dynamicPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private class researchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String)researchComboBox.getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals(researches[0])) {
                    dynamicPanel.removeAll();
                    tablePanel.removeAll();
                    daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
                    monthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
                    yearSpinner = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 1900, 2100, 1));

                    dynamicPanel.add(new JLabel("Jour:"));
                    dynamicPanel.add(daySpinner);
                    dynamicPanel.add(new JLabel("Mois:"));
                    dynamicPanel.add(monthSpinner);
                    dynamicPanel.add(new JLabel("Année:"));
                    dynamicPanel.add(yearSpinner);

                    revalidate();
                    repaint();
                }
                else {
                    if (selectedItem.equals(researches[1])) {
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        tresholdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
                        dynamicPanel.add(new JLabel("Seuil:"));
                        dynamicPanel.add(tresholdSpinner);

                        dynamicPanel.revalidate();
                        dynamicPanel.repaint();
                    }
                    else {
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        ArrayList<Product> products = productController.getAllProducts();
                        productJList = new JList<>(products.toArray(new Product[0]));
                        productJList.setVisibleRowCount(5);
                        JScrollPane listScroll = new JScrollPane(productJList);
                        dynamicPanel.add(new JLabel("Choisir un produit :"));
                        dynamicPanel.add(listScroll);

                        revalidate();
                        repaint();
                    }
                }
            }
        }
    }

    public void setResearchesController(ResearchesController researchesController) {
        this.researchesController = researchesController;
    }

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    private class ResearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String)researchComboBox.getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals(researches[0])) {
                    int day = (int) daySpinner.getValue();
                    int month = (int) monthSpinner.getValue();
                    int year = (int) yearSpinner.getValue();
                    LocalDate date = LocalDate.of(year, month, day);

                    alcoholicDrinksModel = new AlcoholicDrinksModel(researchesController.getAlcDrinksBeforeDate(date));
                    showTable(alcoholicDrinksModel);
                }
                else {
                    if (selectedItem.equals(researches[1])) {
                        int treshold = (int)tresholdSpinner.getValue();

                        productsUnderThresholdModel = new ProductsUnderThresholdModel(researchesController.getProductsUnderThreshold(treshold));
                        showTable(productsUnderThresholdModel);
                    }
                    else {
                        int selectedProductId = productJList.getSelectedValue().getId();

                        orderInfosModel = new OrderInfosModel(researchesController.getAllOrdersInfos(selectedProductId));
                        showTable(orderInfosModel);
                    }
                }
            }
        }
    }
    private void showTable(AbstractTableModel model) {
        tablePanel.removeAll();

        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(50);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 800));
        tablePanel.add(scrollPane);

        tablePanel.revalidate();
        tablePanel.repaint();
    }
}
