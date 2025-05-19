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
    private JPanel formPanel, buttonsPanel;
    private JTextField label, price, alcoholPercentage, treshold, supplierName, supplierPhone, nbStock, description;
    private JComboBox<String> category;
    private JRadioButton glutenFree, isAlcohol;
    private JButton cancelButton, submitButton, resetButton;

    public CreateProductPanel() {
        setLayout(new BorderLayout());
        this.productController = new ProductController();

        formPanel = new JPanel(new GridLayout(11, 2));
        buttonsPanel = new JPanel(new FlowLayout());

        label = new JTextField();
        price = new JTextField();
        alcoholPercentage = new JTextField();
        alcoholPercentage.setEnabled(false);
        treshold = new JTextField();
        nbStock = new JTextField();
        description = new JTextField();
        supplierName = new JTextField();
        supplierPhone = new JTextField();

        category = new JComboBox<>(categories);
        category.setSelectedIndex(-1);

        glutenFree = new JRadioButton("Sans gluten");
        isAlcohol = new JRadioButton("Alcoolisé");

        // Ajout des listeners sous forme de classes privées internes
        glutenFree.addItemListener(new GlutenFreeItemListener());
        isAlcohol.addItemListener(new IsAlcoholItemListener());

        formPanel.add(new JLabel("Nom du produit :"));
        formPanel.add(label);

        formPanel.add(new JLabel("Prix du produit :"));
        formPanel.add(price);

        formPanel.add(new JLabel("Nombre en stock :"));
        formPanel.add(nbStock);

        formPanel.add(new JLabel("Minimum avant notification :"));
        formPanel.add(treshold);

        formPanel.add(glutenFree);
        formPanel.add(isAlcohol);

        formPanel.add(new JLabel("Pourcentage d'alcool :"));
        formPanel.add(alcoholPercentage);

        formPanel.add(new JLabel("Catégorie :"));
        formPanel.add(category);

        formPanel.add(new JLabel("Description"));
        formPanel.add(description);

        formPanel.add(new JLabel("Nom du fournisseur :"));
        formPanel.add(supplierName);

        formPanel.add(new JLabel("Numéro du fournisseur :"));
        formPanel.add(supplierPhone);

        cancelButton = new JButton("Annuler");
        resetButton = new JButton("Réinitialiser");
        submitButton = new JButton("Ajouter le produit");

        cancelButton.addActionListener(new CancelButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // Classes privées internes pour listeners

    private class GlutenFreeItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (glutenFree.isSelected()) {
                isAlcohol.setSelected(false);
                alcoholPercentage.setText("");
                alcoholPercentage.setEnabled(false);
                category.setSelectedIndex(3); // "Sans gluten"
                category.setEnabled(false);
            } else if (!isAlcohol.isSelected()) {
                category.setSelectedIndex(-1);
                category.setEnabled(true);
            }
        }
    }

    private class IsAlcoholItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (isAlcohol.isSelected()) {
                glutenFree.setSelected(false);
                alcoholPercentage.setEnabled(true);
                category.setSelectedIndex(0); // "Boisson alcoolisée"
                category.setEnabled(false);
            } else if (!glutenFree.isSelected()) {
                alcoholPercentage.setText("");
                alcoholPercentage.setEnabled(false);
                category.setSelectedIndex(-1);
                category.setEnabled(true);
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
            label.setText("");
            price.setText("");
            nbStock.setText("");
            treshold.setText("");
            alcoholPercentage.setText("");
            alcoholPercentage.setEnabled(false);
            supplierName.setText("");
            supplierPhone.setText("");
            glutenFree.setSelected(false);
            isAlcohol.setSelected(false);
            category.setSelectedIndex(-1);
            category.setEnabled(true);
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String productLabel = label.getText().trim();
                double productPrice = Double.parseDouble(price.getText().trim());
                int stock = Integer.parseInt(nbStock.getText().trim());
                int productThreshold = Integer.parseInt(treshold.getText().trim());
                boolean withGluten = !glutenFree.isSelected(); // inversion logique
                String productDescription = description.getText();
                String supplierLabel = supplierName.getText().trim();
                int supplierPhoneNb = Integer.parseInt(supplierPhone.getText().trim());
                String productCategory = (String) category.getSelectedItem();
                LocalDate today = LocalDate.now();
                Double alcoholPct = null;

                if (isAlcohol.isSelected()) {
                    alcoholPct = Double.parseDouble(alcoholPercentage.getText().trim());
                }

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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer des valeurs numériques valides pour les champs concernés.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}