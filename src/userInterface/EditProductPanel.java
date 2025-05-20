package userInterface;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class EditProductPanel extends JPanel {
    private Product product;
    private ProductController productController;

    private JTextField label, price, alcoholPercentage, treshold, supplierName, supplierPhone, nbStock, description;
    private JComboBox<String> category;
    private JRadioButton glutenFree, isAlcohol;
    private JButton cancelButton, saveButton;

    private final String[] categories = {
            "Boisson alcoolisée", "Soft", "Snacks", "Sans gluten",
            "Boisson chaude", "Glace"
    };

    public EditProductPanel(Product product) {
        this.product = product;
        setProductController(new ProductController());

        setLayout(new BorderLayout());

        initFormComponents();
        loadFields();

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        cancelButton = new JButton("Annuler");
        saveButton = new JButton("Enregistrer");

        cancelButton.addActionListener(new CancelButtonListener());
        saveButton.addActionListener(new SaveButtonListener());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        add(createFormPanel(), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    //initialiser les composants du formulaire
    private void initFormComponents() {
        label = new JTextField();
        price = new JTextField();
        alcoholPercentage = new JTextField();
        treshold = new JTextField();
        nbStock = new JTextField();
        description = new JTextField();
        supplierName = new JTextField();
        supplierPhone = new JTextField();

        category = new JComboBox<>(categories);
        glutenFree = new JRadioButton("Sans gluten");
        isAlcohol = new JRadioButton("Alcoolisé");

        glutenFree.addItemListener(new GlutenFreeItemListener());
        isAlcohol.addItemListener(new IsAlcoholItemListener());
    }

    //Créé le formulaire
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(11, 2));

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

        return formPanel;
    }

    //va remplir les textFields du formulaire
    private void loadFields() {
        label.setText(product.getLabel());
        price.setText(String.valueOf(product.getPrice()));
        nbStock.setText(String.valueOf(product.getNbInStock()));
        treshold.setText(String.valueOf(product.getMinTreshold()));
        description.setText(product.getDescription());
        supplierName.setText(product.getSupplierLabel());
        supplierPhone.setText(String.valueOf(product.getSupplierPhoneNumber()));

        glutenFree.setSelected(!product.getGlutenFree());
        isAlcohol.setSelected(product.getAlcoholPercentage() != null);

        if (product.getAlcoholPercentage() != null) {
            alcoholPercentage.setText(String.valueOf(product.getAlcoholPercentage()));
            alcoholPercentage.setEnabled(true);
        } else {
            alcoholPercentage.setText("");
            alcoholPercentage.setEnabled(false);
        }

        if (product.getCategoryLabel() != null) {
            category.setSelectedItem(product.getCategoryLabel());
        } else {
            category.setSelectedIndex(-1);
        }

        category.setEnabled(!(glutenFree.isSelected() || isAlcohol.isSelected()));
    }

    private class GlutenFreeItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (glutenFree.isSelected()) {
                isAlcohol.setSelected(false);
                alcoholPercentage.setText("");
                alcoholPercentage.setEnabled(false);
                category.setSelectedItem("Sans gluten");
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
                category.setSelectedItem("Boisson alcoolisée");
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
            removeAll();
            UpdateProductPanel updateProductPanel = new UpdateProductPanel();
            add(updateProductPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String newLabel = label.getText();
                Double newPrice = Double.parseDouble(price.getText());
                Integer newStock = Integer.parseInt(nbStock.getText());
                Integer newTreshold = Integer.parseInt(treshold.getText());
                Boolean newGlutenFree = glutenFree.isSelected();
                String newDescription = description.getText();
                String newSupplierName = supplierName.getText();
                Integer newSupplierPhone = Integer.parseInt(supplierPhone.getText());

                Double newAlcoholPercentage = null;
                if (isAlcohol.isSelected()) {
                    newAlcoholPercentage = Double.parseDouble(alcoholPercentage.getText());
                }

                String newCategory = (String) category.getSelectedItem();

                LocalDate distributionDate = product.getDistributionDate();
                LocalDate lastRestockDate = product.getLastRestockDate();
                if (!product.getNbInStock().equals(newStock)) {
                    lastRestockDate = LocalDate.now();
                }

                Product updatedProduct = new Product(
                        product.getId(),
                        newLabel,
                        newPrice,
                        newStock,
                        newTreshold,
                        newGlutenFree,
                        newAlcoholPercentage,
                        distributionDate,
                        lastRestockDate,
                        newDescription,
                        newSupplierName,
                        newSupplierPhone,
                        newCategory
                );
                productController.updateProduct(updatedProduct);
                JOptionPane.showMessageDialog(EditProductPanel.this, "Produit mis à jour avec succès !");
                removeAll();
                add(new UpdateProductPanel());
                revalidate();
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EditProductPanel.this, "Erreur : vérifiez les champs numériques.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EditProductPanel.this, "Erreur inattendue : " + ex.getMessage());
            }
        }
    }



    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
}
