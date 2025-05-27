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

public class EditProductPanel extends JPanel {
    private JTextField label, price, alcoholPercentage, treshold, supplierName, supplierPhone, nbStock, description;
    private JComboBox<String> category;
    private JCheckBox glutenFreeCheckBox, isAlcoholCheckBox;
    private JButton cancelButton, saveButton;

    private Product product;
    private ProductController productController;
    private CategoryController categoryController;
    private SupplierController supplierController;

    public EditProductPanel(Product product) {
        setLayout(new BorderLayout());
        setProductController(new ProductController());
        setCategoryController(new CategoryController());
        setSupplierController(new SupplierController());
        this.product = product;

        // Création du panel global
        initFormComponents();
        loadFields();

        // Panel des boutons
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        cancelButton = new JButton("Annuler");
        saveButton = new JButton("Enregistrer");
            // Listeners
        cancelButton.addActionListener(new CancelButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        // Ajout au ButtonsPanel
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        // Ajout au EditProductPanel
        add(createFormPanel(), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // SETTERS
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
    public void setSupplierController(SupplierController supplierController) {
        this.supplierController = supplierController;
    }
    public void setExistingPhoneNumber(){
        SwingUtilities.invokeLater(() -> {
            try {
                String phone = supplierController.getSupplierPhoneNumberByName(supplierName.getText().trim());
                if (phone != null && !phone.isEmpty()) {
                    supplierPhone.setText(phone);
                    supplierPhone.setEnabled(false);
                } else {
                    supplierPhone.setEnabled(true);
                    supplierPhone.setText("");
                }
            } catch (DAOException daoException) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la récupération du numéro du fournisseur");
            }
        });
    }

    // METHODES
        // Déclare les composants du formulaire
    private void initFormComponents() {
        label = new JTextField();
        price = new JTextField();
        alcoholPercentage = new JTextField();
        treshold = new JTextField();
        nbStock = new JTextField();
        description = new JTextField();
        supplierName = new JTextField();
        supplierPhone = new JTextField();

        glutenFreeCheckBox = new JCheckBox("Sans gluten");
        isAlcoholCheckBox = new JCheckBox("Alcoolisé");

        try {
            category = new JComboBox<>(categoryController.getAllCategories().toArray(new String[0]));
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des catégories");
        }

        // Listeners
        supplierName.getDocument().addDocumentListener(new RefreshPhoneNumbeListener());
        category.addItemListener(new CategoryItemListener());
        isAlcoholCheckBox.addItemListener(new IsAlcoholItemListener());
    }
        // Créé le formulaire
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

        formPanel.add(glutenFreeCheckBox);
        formPanel.add(isAlcoholCheckBox);

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
        // Remplie les textFields du formulaire
    private void loadFields() {
        label.setText(product.getLabel());
        price.setText(String.valueOf(product.getPrice()));
        nbStock.setText(String.valueOf(product.getNbInStock()));
        treshold.setText(String.valueOf(product.getMinTreshold()));
        description.setText(product.getDescription());
        supplierName.setText(product.getSupplierLabel());
        supplierPhone.setText(product.getSupplierPhoneNumber());

        glutenFreeCheckBox.setSelected(product.getGlutenFree());
        // Si non-null -> est alcoolisé
        if (product.getAlcoholPercentage() != null) {
            isAlcoholCheckBox.setSelected(true);
            alcoholPercentage.setText(String.valueOf(product.getAlcoholPercentage()));
            alcoholPercentage.setEnabled(true);
        } else {
            isAlcoholCheckBox.setSelected(false);
            alcoholPercentage.setText("");
            alcoholPercentage.setEnabled(false);
        }

        if (product.getCategoryLabel() != null) {
            category.setSelectedItem(product.getCategoryLabel());
        } else {
            category.setSelectedIndex(-1);
        }

        category.setEnabled(!(isAlcoholCheckBox.isSelected()));
    }

    // LISTENERS
    private class IsAlcoholItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (isAlcoholCheckBox.isSelected()) {
                alcoholPercentage.setEnabled(true);
                category.setSelectedItem("Boisson alcoolisée");
                category.setEnabled(false);
            } else {
                alcoholPercentage.setText("");
                alcoholPercentage.setEnabled(false);
                category.setSelectedIndex(-1);
                category.setEnabled(true);
            }
        }
    }
        // Si il choisit alcool comme catégorie alors la radio devient true
    private class CategoryItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = (String) e.getItem();
                if ("Boisson alcoolisée".equals(selected)) {
                    isAlcoholCheckBox.setSelected(true);
                    category.setEnabled(false);
                }
            }
        }
    }
    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeAll();
            add(new UpdateProductPanel(), BorderLayout.CENTER);

            revalidate();
            repaint();
        }
    }
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String newLabel = label.getText();
                Double newPrice = Double.parseDouble(price.getText().trim());
                Integer newStock = Integer.parseInt(nbStock.getText().trim());
                Integer newTreshold = Integer.parseInt(treshold.getText().trim());
                Boolean newGlutenFree = glutenFreeCheckBox.isSelected();  // Checkbox indépendante
                String newDescription;
                String newSupplierName = supplierName.getText();
                String newSupplierPhone = supplierPhone.getText().trim();

                Double newAlcoholPercentage = null;
                if (isAlcoholCheckBox.isSelected()) {
                    String alcText = alcoholPercentage.getText().trim();
                    if (!alcText.isEmpty()) {
                        try {
                            newAlcoholPercentage = Double.parseDouble(alcText);
                            if (newAlcoholPercentage < 1 || newAlcoholPercentage > 100) {
                                JOptionPane.showMessageDialog(EditProductPanel.this, "Le taux d'alcool doit être entre 1 et 100%.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(EditProductPanel.this, "Le taux d'alcool doit être un nombre valide.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(EditProductPanel.this, "Veuillez entrer un taux d'alcool.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String newCategory = (String) category.getSelectedItem();
                if (newCategory == null || newCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(EditProductPanel.this, "Veuillez sélectionner une catégorie.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate distributionDate = product.getDistributionDate();
                LocalDate lastRestockDate = product.getLastRestockDate();

                if (!product.getNbInStock().equals(newStock)) {
                    lastRestockDate = LocalDate.now();
                }

                if (description.getText().trim().isEmpty()) {
                    newDescription = null;
                } else {
                    newDescription  = description.getText();
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
                JOptionPane.showMessageDialog(null, "Erreur : vérifiez les champs numériques.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur inattendue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
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
}
