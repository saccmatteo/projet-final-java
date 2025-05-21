package userInterface;

import controller.CategoryController;
import controller.ProductController;
import controller.ResearchesController;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Supplier;

public class ResearchPanel extends JPanel {
    private String [] researches = {"Boissons alcoolisées", "Produit en dessous du seuil", "Informations commande d'un produit", "Moyenne mensuelle (6 mois)"};

    private JPanel researchPanel, tablePanel, dynamicPanel;
    private JLabel averageProductSelledByMonthLabel, categoryLabel;
    private JComboBox researchComboBox, categoryCombobox;
    private JButton researchButton;
    private JScrollPane scrollPane, listScroll;
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
            // J trucs
        researchComboBox = new JComboBox(researches);
        researchComboBox.setSelectedIndex(-1);
        researchButton = new JButton("Effectuer la recherche");

        // Listeners
        researchComboBox.addActionListener(new researchListener());
        researchButton.addActionListener(new ResearchButtonListener());

        // Ajout au panels
            // North
        researchPanel.add(researchComboBox);
        researchPanel.add(researchButton);
        this.add(researchPanel, BorderLayout.NORTH);
            // CENTER
        this.add(dynamicPanel, BorderLayout.CENTER);
        dynamicPanel.setBorder(new EmptyBorder(25, 0, 0,0));
            // SOUTH
        this.add(tablePanel, BorderLayout.SOUTH);
        tablePanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        this.setVisible(true);
    }

    private class researchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String)researchComboBox.getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Boissons alcoolisées":
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        // Initialisation des variables
                        daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
                        monthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
                        yearSpinner = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 1900, 2100, 1));

                        //pour enlever l'espace inutile dans le JSpinner de l'année 2
                        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
                        yearSpinner.setEditor(editor);

                        // Ajout au panelDynamic (South)
                        dynamicPanel.add(new JLabel("Jour:"));
                        dynamicPanel.add(daySpinner);
                        dynamicPanel.add(new JLabel("Mois:"));
                        dynamicPanel.add(monthSpinner);
                        dynamicPanel.add(new JLabel("Année:"));
                        dynamicPanel.add(yearSpinner);

                        revalidate();
                        repaint();
                        break;
                    case "Produit en dessous du seuil":
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        // Initialisation des variables
                        tresholdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

                        // Ajout au panelDynamic (South)
                        dynamicPanel.add(new JLabel("Seuil:"));
                        dynamicPanel.add(tresholdSpinner);

                        dynamicPanel.revalidate();
                        dynamicPanel.repaint();
                        break;
                    case "Informations commande d'un produit":
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();

                        createProductList();

                        revalidate();
                        repaint();
                        break;
                    case "Moyenne mensuelle (6 mois)":
                        dynamicPanel.removeAll();
                        tablePanel.removeAll();
                        averageProductSelledByMonthLabel = new JLabel();

                        createProductList();

                        revalidate();
                        repaint();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // SETTERS
        // Controller
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
        listScroll = new JScrollPane(productJList);
        listScroll.setPreferredSize(new Dimension(300, 60));
        listScroll.setVisible(false);

        categoryCombobox = new JComboBox(categoryController.getAllCategories().toArray(new String[0]));
        categoryCombobox.setSelectedIndex(-1);
        categoryCombobox.addActionListener(new CategoryComboboxListener());

        categoryLabel = new JLabel("Choisir un produit :");
        categoryLabel.setVisible(false);
        dynamicPanel.add(categoryLabel);
        dynamicPanel.add(listScroll);
        dynamicPanel.add(categoryCombobox);
    }

    private void showTable(AbstractTableModel model) {
        tablePanel.removeAll();

        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(50);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 750));
        tablePanel.add(scrollPane);

        tablePanel.revalidate();
        tablePanel.repaint();
    }

    // LISTENERS
    private class ResearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String)researchComboBox.getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Boissons alcoolisées":
                        int day = (int) daySpinner.getValue();
                        int month = (int) monthSpinner.getValue();
                        int year = (int) yearSpinner.getValue();
                        LocalDate date = LocalDate.of(year, month, day);

                        alcoholicDrinksModel = new AlcoholicDrinksModel(researchesController.getAlcDrinksBeforeDate(date));
                        showTable(alcoholicDrinksModel);
                        break;
                    case "Produit en dessous du seuil":
                        int treshold = (int)tresholdSpinner.getValue();

                        productsUnderThresholdModel = new ProductsUnderThresholdModel(researchesController.getProductsUnderThreshold(treshold));
                        showTable(productsUnderThresholdModel);
                        break;
                    case "Informations commande d'un produit":
                        int selectedProductId = productJList.getSelectedValue().getId();

                        orderInfosModel = new OrderInfosModel(researchesController.getAllOrdersInfos(selectedProductId));
                        showTable(orderInfosModel);
                        break;
                    case "Moyenne mensuelle (6 mois)":
                        selectedProductId = productJList.getSelectedValue().getId();
                        averageProductSelledByMonthLabel.setText("Moyenne mensuelle des ventes de " + productJList.getSelectedValue().getLabel() + " sur les 6 derniers mois : " + String.format("%.2f", productController.getAllProductSelledLast6Months(selectedProductId) / 6.0));
                        averageProductSelledByMonthLabel.setFont(new Font("Arial", Font.BOLD, 24));
                        tablePanel.add(averageProductSelledByMonthLabel);

                        tablePanel.revalidate();
                        tablePanel.repaint();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private void updateProductList(String category) {
        //nouvelle liste filtrée avec ceux qui ont la mm categorie que la combobox
        ArrayList<Product> filteredProducts = new ArrayList<>();
        ArrayList<Product> products = productController.getAllProducts();

        //pour chaque produits de la db on check la category si elle equals la combobox
        for (Product product : products) {
            if (product.getCategoryLabel().equals(category)) {
                filteredProducts.add(product);
            }
        }
        categoryLabel.setVisible(true);
        listScroll.setVisible(true);
        //on setListData avec la nouvelle ArrayList filtrée
        productJList.setListData(filteredProducts.toArray(new Product[0]));
    }
    private class CategoryComboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //CASTING OBLIGATOIRE CAR LE SELECTED ITEM EST UN OBJECT ET PAS UN STRING
            String selectedCategory = (String) categoryCombobox.getSelectedItem();
            updateProductList(selectedCategory);
        }
    }
}
