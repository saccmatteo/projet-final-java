package userInterface;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class CreateProductPanel extends JPanel {
    private final String[] categories = {
            "Boisson alcoolisée", "Soft", "Snacks",
            "Boisson chaude", "Glace"
    };

    private ProductController productController;

    private JTextField labelField, priceField, alcoholPercentageField, tresholdField, supplierNameField,
            supplierPhoneField, nbStockField, descriptionField;
    private JComboBox<String> categoryBox;
    private JCheckBox glutenFreeCheckBox, isAlcoholCheckBox;
    private JButton cancelButton, submitButton, resetButton;

    public CreateProductPanel() {
        setLayout(new BorderLayout());
        this.productController = new ProductController();

        initFormComponents();

        cancelButton = new JButton("Annuler");
        resetButton = new JButton("Réinitialiser");
        submitButton = new JButton("Ajouter le produit");

        cancelButton.addActionListener(new CancelButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(submitButton);

        add(createFormPanel(), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    //initialiser les composants
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

        categoryBox = new JComboBox<>(categories);
        categoryBox.setSelectedIndex(-1);
        categoryBox.addItemListener(new CategoryItemListener());

        glutenFreeCheckBox = new JCheckBox("Sans gluten");
        isAlcoholCheckBox = new JCheckBox("Alcoolisé");

        isAlcoholCheckBox.addItemListener(new IsAlcoholItemListener());
    }

    //créé le formulaire
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(11, 2, 10, 10));

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

        return formPanel;
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
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String productLabel = labelField.getText().trim();
                double productPrice = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(nbStockField.getText().trim());
                int productThreshold = Integer.parseInt(tresholdField.getText().trim());
                boolean withGluten = glutenFreeCheckBox.isSelected();
                String productDescription = descriptionField.getText();
                String supplierLabel = supplierNameField.getText().trim();
                String supplierPhoneNb = supplierPhoneField.getText().trim();

                String productCategory = (String) categoryBox.getSelectedItem();
                if (productCategory == null || productCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une catégorie.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate today = LocalDate.now();

                Double alcoholPct = null;
                if (isAlcoholCheckBox.isSelected()) {
                    String alcoholText = alcoholPercentageField.getText().trim();
                    if (alcoholText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un taux d'alcool entre 1 et 100.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        alcoholPct = Double.parseDouble(alcoholText);
                        if (alcoholPct < 1 || alcoholPct > 100) {
                            JOptionPane.showMessageDialog(null, "Le taux d'alcool doit être entre 1 et 100%.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Le taux d'alcool doit être un nombre valide.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                        return;
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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer des valeurs numériques valides pour les champs concernés.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}