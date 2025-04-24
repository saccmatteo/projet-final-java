package userInterface;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;

public class CreateProductForm extends JFrame {
    private String[] categories = {"Boisson alcoolisée", "Soft", "Snacks", "Sans gluten", "Boisson chaude", "Glace"};
    private ProductController productController;
    private Container container;
    private JPanel formPanel, buttonsPanel;
    private JTextField label, price, alcoholPercentage, treshold, supplierName, supplierPhone, nbStock;
    private JComboBox category;
    private JCheckBox glutenFree, isAlcohol;
    private JButton cancelButton, submitButton, resetButton;

    public CreateProductForm() {
        setTitle("Formulaire d'ajout de produit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(800, 300, 500, 500);
        setProductController(new ProductController());

        // Container
        container = getContentPane();
        container.setLayout(new BorderLayout());

        // PANEL FORM
        // Declaration
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        label = new JTextField();
        price = new JTextField();
        alcoholPercentage = new JTextField();
        treshold = new JTextField();
        supplierName = new JTextField();
        supplierPhone = new JTextField();
        nbStock = new JTextField();
        category = new JComboBox(categories);
        glutenFree = new JCheckBox("Sans gluten");
        isAlcohol = new JCheckBox("Alcoolisé");

            // Listeners
                // Alcohol
        isAlcohol.addItemListener(new AlcoholListener());

            // Ajout
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
        alcoholPercentage.setEnabled(false);
        formPanel.add(alcoholPercentage);
        formPanel.add(new JLabel("Catégorie :"));
        category.setSelectedIndex(-1); // Pour qu'il y ait rien au debut
        formPanel.add(category);
        formPanel.add(new JLabel("Nom du fournisseur :"));
        formPanel.add(supplierName);
        formPanel.add(new JLabel("Numéro du fournisseur :"));
        formPanel.add(supplierPhone);

        // PANEL BUTTONS
            // Declaration
        cancelButton = new JButton("Annuler");
        resetButton = new JButton("Réinitialiser");
        submitButton = new JButton("Ajouter le produit");

            // Listeners
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateProductForm();
            }
        });
        submitButton.addActionListener(new SubmitButtonListener());

            // Ajout
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(submitButton);

            // Ajout au container
        container.add(formPanel, BorderLayout.NORTH);
        container.add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // SETTERS
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

// LISTENERS
    private class AlcoholListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                category.setSelectedIndex(0);
                alcoholPercentage.setEnabled(true);
            }else{
                alcoholPercentage.setText("");
                category.setSelectedIndex(-1);
                alcoholPercentage.setEnabled(false);
            }
        }
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isAlcohol.isSelected()) {
                productController.createProduct(
                        new Product(null,
                                label.getText(),
                                Double.parseDouble(price.getText()),
                                Integer.parseInt(nbStock.getText()),
                                Integer.parseInt(treshold.getText()),
                                glutenFree.isSelected(),
                                Double.parseDouble(alcoholPercentage.getText()),
                                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                                null,
                                supplierName.getText(),
                                Integer.parseInt(supplierPhone.getText()),
                                (String)(category.getSelectedItem())
                        ));
            }else{
                productController.createProduct(
                        new Product(null,
                                label.getText(),
                                Double.parseDouble(price.getText()),
                                Integer.parseInt(nbStock.getText()),
                                Integer.parseInt(treshold.getText()),
                                glutenFree.isSelected(),
                                null,
                                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                                null,
                                supplierName.getText(),
                                Integer.parseInt(supplierPhone.getText()),
                                (String)(category.getSelectedItem())
                        ));
            }
        }
    }
}
