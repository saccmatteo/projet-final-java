package userInterface;

import controller.CategoryController;
import controller.ProductController;
import controller.ResearchesController;
import jdk.jfr.Category;
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
    private JPanel researchPanel, tablePanel, dynamicPanel;
    private JLabel averageProductSelledByMonthLabel;
    private JComboBox researchComboBox;
    private JButton researchButton;
    private JScrollPane scrollPane;
    private JTable table;
    private JSpinner daySpinner, monthSpinner, yearSpinner, tresholdSpinner;
    private JList<Product> productJList;

    private AlcoholicDrinksModel alcoholicDrinksModel;
    private ProductsUnderThresholdModel productsUnderThresholdModel;
    private OrderInfosModel orderInfosModel;

    private ResearchesController researchesController;
    private ProductController productController;
    private CategoryController categoryController;

    public ResearchPanel() {
        // Initialisation des variables
        setLayout(new BorderLayout());
        setResearchesController(new ResearchesController());
        setProductController(new ProductController());
        setCategoryController(new CategoryController());

        // Panels
        researchPanel = new JPanel();
        dynamicPanel = new JPanel();
        tablePanel = new JPanel();
            // ResearchCombobox
        researchComboBox = new JComboBox(categoryController.getAllCategories().toArray(new String[0]));
        researchComboBox.setSelectedIndex(-1);
        researchButton = new JButton("Effectuer la recherche");
            // Listeners
        researchComboBox.addActionListener(new ResearchComboboxListener());
        researchButton.addActionListener(new ResearchButtonListener());

        // Ajout au panels
            // North
        researchPanel.add(researchComboBox);
        researchPanel.add(researchButton);
        this.add(researchPanel, BorderLayout.NORTH);
            // Center
        this.add(tablePanel, BorderLayout.CENTER);
            // South
        this.add(dynamicPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    // SETTERS
    public void setResearchesController(ResearchesController researchesController) {
        this.researchesController = researchesController;
    }
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    // METHODES
    public void createProductList(){
        ArrayList<Product> products = productController.getAllProducts();
        productJList = new JList<>(products.toArray(new Product[0]));
        productJList.setVisibleRowCount(5);

        JScrollPane listScroll = new JScrollPane(productJList);
        dynamicPanel.add(new JLabel("Choisir un produit :"));
        dynamicPanel.add(listScroll);
    }
    private void showTable(AbstractTableModel model) {
        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(50);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 800));
        tablePanel.add(scrollPane);

        tablePanel.revalidate();
        tablePanel.repaint();
    }

    // LISTENERS
    private class ResearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int indexSelectedItem = researchComboBox.getSelectedIndex();
            if (researchComboBox.getSelectedItem() != null) {
                switch (indexSelectedItem) {
                    case 0:
                        int day = (int) daySpinner.getValue();
                        int month = (int) monthSpinner.getValue();
                        int year = (int) yearSpinner.getValue();
                        LocalDate date = LocalDate.of(year, month, day);

                        alcoholicDrinksModel = new AlcoholicDrinksModel(researchesController.getAlcDrinksBeforeDate(date));
                        showTable(alcoholicDrinksModel);
                        break;
                    case 1:
                        int treshold = (int)tresholdSpinner.getValue();

                        productsUnderThresholdModel = new ProductsUnderThresholdModel(researchesController.getProductsUnderThreshold(treshold));
                        showTable(productsUnderThresholdModel);
                        break;
                    case 2:
                        int selectedProductId = productJList.getSelectedValue().getId();

                        orderInfosModel = new OrderInfosModel(researchesController.getAllOrdersInfos(selectedProductId));
                        showTable(orderInfosModel);
                        break;
                    case 3:
                        selectedProductId = productJList.getSelectedValue().getId();
                        averageProductSelledByMonthLabel.setText("Moyenne par mois : " + String.format("%.2f", productController.getAverageProductSelledByMonth(selectedProductId)));

                        tablePanel.revalidate();
                        tablePanel.repaint();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private class ResearchComboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int indexSelectedItem = researchComboBox.getSelectedIndex();
            if (researchComboBox.getSelectedItem() != null) {
                switch (indexSelectedItem) {
                    case 0:
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        // Initialisation des variables
                        daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
                        monthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
                        yearSpinner = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 1900, 2100, 1));

                        // Ajout au panelDynamic (South)
                        dynamicPanel.add(new JLabel("Jour :"));
                        dynamicPanel.add(daySpinner);
                        dynamicPanel.add(new JLabel("Mois :"));
                        dynamicPanel.add(monthSpinner);
                        dynamicPanel.add(new JLabel("Année :"));
                        dynamicPanel.add(yearSpinner);

                        revalidate();
                        repaint();
                        break;
                    case 1:
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        // Initialisation des variables
                        tresholdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

                        // Ajout au panelDynamic (South)
                        dynamicPanel.add(new JLabel("Seuil :"));
                        dynamicPanel.add(tresholdSpinner);

                        dynamicPanel.revalidate();
                        dynamicPanel.repaint();
                        break;
                    case 2:
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        createProductList();

                        revalidate();
                        repaint();
                        break;
                    case 3:
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        averageProductSelledByMonthLabel = new JLabel("Moyenne par mois : ");
                        createProductList();
                        tablePanel.add(averageProductSelledByMonthLabel);

                        revalidate();
                        repaint();
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
