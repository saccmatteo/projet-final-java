package userInterface;

import controller.*;
import exceptions.DAOException;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ResearchPanel extends JPanel {
    private String [] researches = {"Ventes de boissons alcoolisées depuis une date", "Produit en dessous d'un seuil", "Informations commandes d'un produit", "Moyenne mensuelle (6 mois)"};

    private JPanel researchPanel, tablePanel, dynamicPanel;
    private JLabel averageProductSelledByMonthLabel, categoryLabel;
    private JComboBox researchComboBox, categoryComboBox;
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
            // ResearchComboBox
        researchComboBox = new JComboBox(researches);
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
            // CENTER
        this.add(dynamicPanel, BorderLayout.CENTER);
        dynamicPanel.setBorder(new EmptyBorder(25, 0, 0,0));
            // SOUTH
        this.add(tablePanel, BorderLayout.SOUTH);
        tablePanel.setBorder(new EmptyBorder(0, 0, 25, 0));

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
        try {
            // Product JList
            ArrayList<Product> products = productController.getAllProducts();
            productJList = new JList<>(products.toArray(new Product[0]));
            productJList.setVisibleRowCount(5);
            // JScrollPane
            listScroll = new JScrollPane(productJList);
            listScroll.setVisible(false);

            // Category Label
            categoryLabel = new JLabel("Choisir un produit :");
            categoryLabel.setVisible(false);
            // Category ComboBox
            categoryComboBox = new JComboBox(categoryController.getAllCategories().toArray(new String[0]));
            categoryComboBox.setSelectedIndex(-1);
            categoryComboBox.addActionListener(new CategoryComboBoxListener());

            // Ajout au DynamicPanel
            dynamicPanel.add(categoryLabel);
            dynamicPanel.add(listScroll);
            dynamicPanel.add(categoryComboBox);
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la création la liste des produits");
        }
    }
    private void showTable(AbstractTableModel model) {
        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(50);

        // MAJ de la ScrollPane avec le nouveau tableau
        scrollPane = new JScrollPane(table);
        tablePanel.removeAll();
        tablePanel.add(scrollPane);

        tablePanel.revalidate();
        tablePanel.repaint();
    }
    private void updateProductList(String category) throws DAOException {
        try {
            // Nouvelle liste filtrée avec produit : categorie = selectedCategory dans combobox
            ArrayList<Product> filteredProducts = new ArrayList<>();
            ArrayList<Product> products = productController.getAllProducts();

            // Pour chaque produit de la db on check SI p.category = selectedCategory dans combobox
            for (Product product : products) {
                if (product.getCategoryLabel().equals(category)) {
                    filteredProducts.add(product);
                }
            }
            categoryLabel.setVisible(true);
            listScroll.setVisible(true);
            // On setListData avec la nouvelle ArrayList (MAJ)
            productJList.setListData(filteredProducts.toArray(new Product[0]));
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des catégories");
        }
    }

    // LISTENERS
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

                        // Enlève l'espace inutile dans le JSpinner de l'année 2
                        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
                        yearSpinner.setEditor(editor);

                        // Ajout au panelDynamic (South)
                        dynamicPanel.add(new JLabel("Jour :"));
                        dynamicPanel.add(daySpinner);
                        dynamicPanel.add(new JLabel("Mois :"));
                        dynamicPanel.add(monthSpinner);
                        dynamicPanel.add(new JLabel("Année :"));
                        dynamicPanel.add(yearSpinner);
                            // Listeners
                        monthSpinner.addChangeListener(new DateChangeListener());
                        yearSpinner.addChangeListener(new DateChangeListener());

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

                        try {
                            alcoholicDrinksModel = new AlcoholicDrinksModel(researchesController.getAlcDrinksBeforeDate(date));
                            showTable(alcoholicDrinksModel);
                        } catch (DAOException daoException) {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des ventes des boissons alcoolisées");
                        }
                        break;
                    case 1:
                        try {
                            int treshold = (int) tresholdSpinner.getValue();
                            productsUnderThresholdModel = new ProductsUnderThresholdModel(researchesController.getProductsUnderThreshold(treshold));
                            showTable(productsUnderThresholdModel);
                        } catch (DAOException daoException) {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des produits");
                        }
                        break;
                    case 2:
                        if (productJList.getSelectedValue() != null) {
                            try{
                                int selectedProductId = productJList.getSelectedValue().getId();

                                orderInfosModel = new OrderInfosModel(researchesController.getAllOrdersInfos(selectedProductId));
                                showTable(orderInfosModel);
                            } catch (DAOException daoException) {
                                JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des informations de la commande");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,
                                    "Veuillez sélectionner un produit.",
                                    "Aucun produit sélectionné",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 3:
                        if (productJList.getSelectedValue() != null) {
                            try {
                                int selectedProductId = productJList.getSelectedValue().getId();
                                averageProductSelledByMonthLabel.setText("Moyenne mensuelle des ventes de " + productJList.getSelectedValue().getLabel() + " sur les 6 derniers mois : " + String.format("%.2f", productController.calcAverageSalesLast6Months(selectedProductId)));
                                averageProductSelledByMonthLabel.setFont(new Font("Arial", Font.BOLD, 24));
                                tablePanel.add(averageProductSelledByMonthLabel);
                                tablePanel.revalidate();
                                tablePanel.repaint();
                            } catch (DAOException daoException) {
                                JOptionPane.showMessageDialog(null, "Erreur lors du calcul des ventes mensuelles");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Veuillez sélectionner un produit.",
                                    "Aucun produit sélectionné",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private class CategoryComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                updateProductList(selectedCategory);
            } catch (DAOException daoException) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour de la liste de produit");
            }
        }
    }
    private class DateChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int year = (int) yearSpinner.getValue();
            int month = (int) monthSpinner.getValue();

            // Change le nombre de jour max par rapport mois & année
            int maxDays = java.time.YearMonth.of(year, month).lengthOfMonth(); // Nombre de jours dans un mois (annee bissextile et tout)
            SpinnerNumberModel dayModel = (SpinnerNumberModel) daySpinner.getModel();
            dayModel.setMaximum(maxDays);
            int currentDay = (int) dayModel.getValue();
            if (currentDay > maxDays) {
                dayModel.setValue(maxDays);
            }

            // Change l'année max disponible avc l'année actuelle
            SpinnerNumberModel yearModel = (SpinnerNumberModel) yearSpinner.getModel();
            int currentYear = (int) yearModel.getValue();
            if (currentYear > LocalDate.now().getYear()) {
                yearModel.setValue(LocalDate.now().getYear());
            }

        }
    }
}
