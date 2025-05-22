package userInterface;

import controller.*;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListingProductPanel extends JPanel {
    private ProductController productController;
    private CategoryController categoryController;

    private JPanel categoryPanel;
    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;

    private ArrayList<Product> productsList;
    private ArrayList<String> categoryList;

    private JList<Product> productJList;
    private JScrollPane productScrollPane;

    public ListingProductPanel() {
        this.setLayout(new BorderLayout(10,10));
        setProductController(new ProductController());
        setCategoryController(new CategoryController());
        categoryList = categoryController.getAllCategories();
        productsList = productController.getAllProducts();

        // CategoryPanel
        categoryPanel = new JPanel(new FlowLayout());
        categoryLabel = new JLabel("Sélectionnez une catégorie : ");
        categoryComboBox = new JComboBox<>(categoryList.toArray(new String[0]));
        categoryComboBox.setSelectedIndex(-1);
            // Listeners
        categoryComboBox.addActionListener(new CategoryComboboxListener());
            // Style
        categoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        categoryComboBox.setPreferredSize(new Dimension(250, 35));
            // Ajout au CategoryPanel
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        // ProductList et apparence
        productJList = new JList<>();
        productJList.setFont(new Font("Arial", Font.BOLD, 28)); // Agrandir la taille de la police
        productJList.setFixedCellHeight(50); // agrandir les lignes de la JList
        productJList.setBorder(new EmptyBorder(10, 20, 10, 20));
        productJList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Product product = (Product) value;

                label.setText(product.toString());
                label.setHorizontalAlignment(SwingConstants.CENTER); // Centrage horizontal du texte
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                return label;
            }
        });

        // Scrollpane
        productScrollPane = new JScrollPane(productJList);
        productScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajout au panel global
        this.add(categoryPanel, BorderLayout.NORTH);
        this.add(productScrollPane, BorderLayout.CENTER);
    }

    // GETTERS
    public ProductController getProductController() {
        return productController;
    }
    public JComboBox<String> getCategoryComboBox() {
        return categoryComboBox;
    }
    public JList<Product> getProductJList() {
        return productJList;
    }

    // SETTERS
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    // METHODES
    private void updateProductList(String category) {
        // Nouvelle liste filtrée avec produits qui ont la mm categorie selectedCategory
        ArrayList<Product> filteredProducts = new ArrayList<>();

        // Pour chaque produit de la db on check si category = selectedCategory -> add dans ArrayList
        for (Product product : productsList) {
            if (product.getCategoryLabel().equals(category)) {
                filteredProducts.add(product);
            }
        }
        // SetListData avec la nouvelle ArrayList (MAJ)
        productJList.setListData(filteredProducts.toArray(new Product[0]));
    }

        //ici on update la list avec la category puis on réaffiche
    public void refreshAndFilter() {
        this.productsList = productController.getAllProducts(); // Pour MAJ la liste
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory != null) {
            updateProductList(selectedCategory);
        } else {
            productJList.setListData(new Product[0]);
        }
    }

    // LISTENERS
    private class CategoryComboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            updateProductList(selectedCategory);
        }
    }

}
