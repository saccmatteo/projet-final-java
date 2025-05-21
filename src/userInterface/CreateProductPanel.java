package userInterface;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class CreateProductPanel extends JPanel {
    private final String[] categories = {
            "Boisson alcoolisée", "Soft", "Snacks", "Sans gluten",
            "Boisson chaude", "Glace"
    };

    private ProductController productController;

    private JTextField labelField, priceField, alcoholPercentageField, tresholdField, supplierNameField,
            supplierPhoneField, nbStockField, descriptionField;
    private JComboBox<String> categoryBox;
    private JRadioButton glutenFreeRadio, isAlcoholRadio;
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

        glutenFreeRadio = new JRadioButton("Sans gluten");
        isAlcoholRadio = new JRadioButton("Alcoolisé");

        glutenFreeRadio.addItemListener(new GlutenFreeItemListener());
        isAlcoholRadio.addItemListener(new IsAlcoholItemListener());
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

        formPanel.add(glutenFreeRadio);
        formPanel.add(isAlcoholRadio);

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

    private class GlutenFreeItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (glutenFreeRadio.isSelected()) {
                isAlcoholRadio.setSelected(false);
                alcoholPercentageField.setText("");
                alcoholPercentageField.setEnabled(false);
                categoryBox.setSelectedIndex(3); // "Sans gluten"
                categoryBox.setEnabled(false);
            } else if (!isAlcoholRadio.isSelected()) {
                categoryBox.setSelectedIndex(-1);
                categoryBox.setEnabled(true);
            }
        }
    }

    private class IsAlcoholItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (isAlcoholRadio.isSelected()) {
                glutenFreeRadio.setSelected(false);
                alcoholPercentageField.setEnabled(true);
                categoryBox.setSelectedIndex(0); // "Boisson alcoolisée"
                categoryBox.setEnabled(false);
            } else if (!glutenFreeRadio.isSelected()) {
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
            SwingUtilities.getWindowAncestor(CreateProductPanel.this).dispose();
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
            glutenFreeRadio.setSelected(false);
            isAlcoholRadio.setSelected(false);
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
                boolean withGluten = !glutenFreeRadio.isSelected(); // inversion logique
                String productDescription = descriptionField.getText();
                String supplierLabel = supplierNameField.getText().trim();
                String supplierPhoneNb = supplierPhoneField.getText().trim();
                String productCategory = (String) categoryBox.getSelectedItem();
                LocalDate today = LocalDate.now();
                Double alcoholPct = null;

                if (isAlcoholRadio.isSelected()) {
                    alcoholPct = Double.parseDouble(alcoholPercentageField.getText().trim());
                }

                Product newProduct = new Product(
                        productLabel,
                        productPrice,
                        stock,
                        productThreshold,
                        withGluten,
                        alcoholPct, // ou (isAlcoholRadio.isSelected()) ? Double.parseDouble(alcoholPercentageField.getText().trim()) : null,
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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer des valeurs numériques valides pour les champs concernés.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}