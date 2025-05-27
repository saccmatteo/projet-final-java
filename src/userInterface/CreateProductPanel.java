package userInterface;

import controller.CategoryController;
import controller.ProductController;
import controller.SupplierController;
import exceptions.DAOException;
import model.Product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class CreateProductPanel extends JPanel {
    private JPanel buttonsPanel, formPanel;
    private JTextField labelField, priceField, alcoholPercentageField, tresholdField, supplierNameField,
    supplierPhoneField, nbStockField, descriptionField;
    private JComboBox<String> categoryBox;
    private JCheckBox glutenFreeCheckBox, isAlcoholCheckBox;
    private JButton cancelButton, submitButton, resetButton;

    private ProductController productController;
    private SupplierController supplierController;
    private CategoryController categoryController;

    public CreateProductPanel() {
        setLayout(new BorderLayout());
        setProductController( new ProductController());
        setSupplierController(new SupplierController());
        setCategoryController(new CategoryController());

        initFormComponents();
        createFormPanel();

        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // SETTERS
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }
    public void setSupplierController(SupplierController supplierController) {
        this.supplierController = supplierController;
    }

    public void setExistingPhoneNumber(){
        try {
            String phoneNumberExisting = supplierController.getSupplierPhoneNumberByName(supplierNameField.getText());

            if (phoneNumberExisting != null && !phoneNumberExisting.isEmpty()) {
                supplierPhoneField.setText(phoneNumberExisting);
                supplierPhoneField.setEnabled(false);
            } else {
                supplierPhoneField.setEnabled(true);
                supplierPhoneField.setText("");
            }
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération du numéro de téléphone");
        }
    }

    // METHODES
        // Déclare les composants
    private void initFormComponents() {
        labelField = new JTextField();
        priceField = new JTextField();
        alcoholPercentageField = new JTextField();
        alcoholPercentageField.setEnabled(false);
        tresholdField = new JTextField();
        nbStockField = new JTextField();
        descriptionField = new JTextField();
        supplierNameField = new JTextField();
        supplierPhoneField = new JTextField();

        try {
            categoryBox = new JComboBox<>(categoryController.getAllCategories().toArray(new String[0]));
            categoryBox.setSelectedIndex(-1);
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des catégories");
        }

        glutenFreeCheckBox = new JCheckBox("Sans gluten");
        isAlcoholCheckBox = new JCheckBox("Alcoolisé");

        cancelButton = new JButton("Annuler");
        resetButton = new JButton("Réinitialiser");
        submitButton = new JButton("Ajouter le produit");


        // Listeners
        supplierNameField.getDocument().addDocumentListener(new RefreshPhoneNumbeListener());

        categoryBox.addItemListener(new CategoryItemListener());
        isAlcoholCheckBox.addItemListener(new IsAlcoholItemListener());

        cancelButton.addActionListener(new CancelButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());
    }
        // Créé le formulaire
    private void createFormPanel() {
        formPanel = new JPanel(new GridLayout(11, 2, 10, 10));

        formPanel.add(new JLabel("Nom du produit :"));
        formPanel.add(labelField);

        formPanel.add(new JLabel("Prix du produit :"));
        formPanel.add(priceField);

        formPanel.add(new JLabel("Nombre en stock :"));
        formPanel.add(nbStockField);

        formPanel.add(new JLabel("Minimum avant notification :"));
        formPanel.add(tresholdField);

        formPanel.add(glutenFreeCheckBox);
        formPanel.add(isAlcoholCheckBox);

        formPanel.add(new JLabel("Pourcentage d'alcool :"));
        formPanel.add(alcoholPercentageField);

        formPanel.add(new JLabel("Catégorie :"));
        formPanel.add(categoryBox);

        formPanel.add(new JLabel("Description :"));
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Nom du fournisseur :"));
        formPanel.add(supplierNameField);

        formPanel.add(new JLabel("Numéro du fournisseur :"));
        formPanel.add(supplierPhoneField);

        buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(submitButton);

    }

    // LISTENERS
    private class RefreshPhoneNumbeListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            setExistingPhoneNumber();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setExistingPhoneNumber();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // Pas utile dans notre cas
        }
    }
    private class CategoryItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = (String) e.getItem();
                if ("Boisson alcoolisée".equals(selected)) {
                    isAlcoholCheckBox.setSelected(true);
                    categoryBox.setEnabled(false);
                }
            }
        }
    }
    private class IsAlcoholItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (isAlcoholCheckBox.isSelected()) {
                alcoholPercentageField.setEnabled(true);
                categoryBox.setSelectedItem("Boisson alcoolisée");
                categoryBox.setEnabled(false);
            } else {
                alcoholPercentageField.setText("");
                alcoholPercentageField.setEnabled(false);
                categoryBox.setSelectedIndex(-1);
                categoryBox.setEnabled(true);
            }
        }
    }
    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeAll();
            add(new ListingProductPanel());

            revalidate();
            repaint();
        }
    }
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            labelField.setText("");
            priceField.setText("");
            nbStockField.setText("");
            tresholdField.setText("");
            alcoholPercentageField.setText("");
            alcoholPercentageField.setEnabled(false);
            supplierNameField.setText("");
            supplierPhoneField.setText("");
            glutenFreeCheckBox.setSelected(false);
            isAlcoholCheckBox.setSelected(false);
            categoryBox.setSelectedIndex(-1);
            categoryBox.setEnabled(true);
            descriptionField.setText("");
            supplierPhoneField.setEnabled(true);
        }
    }
    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String productLabel = labelField.getText();
                double productPrice = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(nbStockField.getText().trim());
                int productThreshold = Integer.parseInt(tresholdField.getText().trim());
                boolean withGluten = glutenFreeCheckBox.isSelected();
                String productDescription = descriptionField.getText();
                String supplierLabel = supplierNameField.getText();
                String supplierPhoneNb = supplierPhoneField.getText().trim();

                String productCategory = (String) categoryBox.getSelectedItem();
                if (productCategory == null || productCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une catégorie.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                } else {
                    LocalDate today = LocalDate.now();

                    Double alcoholPct = null;
                    if (isAlcoholCheckBox.isSelected()) {
                        String alcoholText = alcoholPercentageField.getText().trim();
                        if (alcoholText.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer un taux d'alcool entre 1 et 100.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            try {
                                alcoholPct = Double.parseDouble(alcoholText);
                                if (alcoholPct < 1 || alcoholPct > 100) {
                                    JOptionPane.showMessageDialog(null, "Le taux d'alcool doit être entre 1 et 100%.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Le taux d'alcool doit être un nombre valide.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                    // Création produit + gestion exceptions
                    try {
                        Product newProduct = new Product(
                                productLabel,
                                productPrice,
                                stock,
                                productThreshold,
                                withGluten,
                                alcoholPct,
                                today,
                                today,
                                productDescription,
                                supplierLabel,
                                supplierPhoneNb,
                                productCategory
                        );
                        productController.createProduct(newProduct);
                        JOptionPane.showMessageDialog(null, "Produit créé avec succès !");

                        new ResetButtonListener().actionPerformed(null);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur...", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer des valeurs numériques valides pour les champs concernés.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}