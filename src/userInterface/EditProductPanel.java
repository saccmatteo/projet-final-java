package userInterface;

import controller.CategoryController;
import controller.ProductController;
import controller.SupplierController;
import model.Product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class EditProductPanel extends JPanel {
    private Product product;
    private ProductController productController;

    private JTextField label, price, alcoholPercentage, treshold, supplierName, supplierPhone, nbStock, description;
    private JComboBox<String> category;
    private JCheckBox glutenFreeCheckBox, isAlcoholCheckBox;
    private JButton cancelButton, saveButton;
    private CategoryController categoryController;
    private SupplierController supplierController;

    public EditProductPanel(Product product) {
        this.product = product;
        setProductController(new ProductController());
        setCategoryController(new CategoryController());
        setSupplierController(new SupplierController());


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
        supplierName.getDocument().addDocumentListener(new RefreshPhoneNumbeListener());
        supplierPhone = new JTextField();


        category = new JComboBox<>(categoryController.getAllCategories().toArray(new String[0]));
        category.addItemListener(new CategoryItemListener());
        glutenFreeCheckBox = new JCheckBox("Sans gluten");
        isAlcoholCheckBox = new JCheckBox("Alcoolisé");

        isAlcoholCheckBox.addItemListener(new IsAlcoholItemListener());
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

    //va remplir les textFields du formulaire
    private void loadFields() {
        label.setText(product.getLabel());
        price.setText(String.valueOf(product.getPrice()));
        nbStock.setText(String.valueOf(product.getNbInStock()));
        treshold.setText(String.valueOf(product.getMinTreshold()));
        description.setText(product.getDescription());
        supplierName.setText(product.getSupplierLabel());
        supplierPhone.setText(String.valueOf(product.getSupplierPhoneNumber()));

        glutenFreeCheckBox.setSelected(product.getGlutenFree());
        isAlcoholCheckBox.setSelected(product.getAlcoholPercentage() != null);

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

        category.setEnabled(!(isAlcoholCheckBox.isSelected()));
    }

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

    //si il choisit alcool comme caté alors la radio devient true
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
                String newLabel = label.getText().trim();
                Double newPrice = Double.parseDouble(price.getText().trim());
                Integer newStock = Integer.parseInt(nbStock.getText().trim());
                Integer newTreshold = Integer.parseInt(treshold.getText().trim());
                Boolean newGlutenFree = glutenFreeCheckBox.isSelected();  // Checkbox indépendante
                String newDescription = description.getText().trim();
                String newSupplierName = supplierName.getText().trim();
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
                JOptionPane.showMessageDialog(EditProductPanel.this, "Erreur : vérifiez les champs numériques.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EditProductPanel.this, "Erreur inattendue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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

    public void setExistingPhoneNumber(){
        SwingUtilities.invokeLater(() -> {
            String phone = supplierController.getSupplierPhoneNumberByName(supplierName.getText().trim());
            if (phone != null && !phone.isEmpty()) {
                supplierPhone.setText(phone);
                supplierPhone.setEnabled(false);
            } else {
                supplierPhone.setEnabled(true);
                supplierPhone.setText("");
            }
        });
    }

    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    public void setSupplierController(SupplierController supplierController) {
        this.supplierController = supplierController;
    }
}
