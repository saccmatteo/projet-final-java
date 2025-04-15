package userInterface;

import controller.CategoryController;
import controller.ProductController;
import jdk.jfr.Category;
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
        productController = new ProductController();
        categoryController = new CategoryController();
        categoryList = categoryController.getAllCategories();
        productsList = productController.getAllProducts();

        //gestion du panel combobox
        categoryPanel = new JPanel(new FlowLayout());
        categoryLabel = new JLabel("Sélectionnez une catégorie : ");
        categoryComboBox = new JComboBox<>(categoryList.toArray(new String[0]));
        categoryComboBox.setSelectedIndex(-1);
        categoryComboBox.addActionListener(new categoryComboboxListener());

        //categoryPanel apparence
        categoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        categoryComboBox.setPreferredSize(new Dimension(250, 35));

        //ajout au panel du choix de category
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        //Jlist et apparence
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

        //Scrollpane et apparence
        productScrollPane = new JScrollPane(productJList);
        productScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //ajout au panel global
        this.add(categoryPanel, BorderLayout.NORTH);
        this.add(productScrollPane, BorderLayout.CENTER);
    }
    private void updateProductList(String category) {
        //nouvelle liste filtrée avec ceux qui ont la mm categorie que la combobox
        ArrayList<Product> filteredProducts = new ArrayList<>();

        //pour chaque produits de la db on check la category si elle equals la combobox
        for (Product product : productsList) {
            if (product.getCategoryLabel().equals(category)) {
                filteredProducts.add(product);
            }
        }
        //on setListData avec la nouvelle ArrayList filtrée
        productJList.setListData(filteredProducts.toArray(new Product[0]));
    }
    //ici on update la list avec la category puis on réaffiche
    public void refreshAndFilter() {
        this.productsList = productController.getAllProducts();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory != null) {
            updateProductList(selectedCategory);
        } else {
            productJList.setListData(new Product[0]);
        }
    }
    private class categoryComboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //CASTING OBLIGATOIRE CAR LE SELECTED ITEM EST UN OBJECT ET PAS UN STRING
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            updateProductList(selectedCategory);
        }
    }

    public JList<Product> getProductJList() {
        return productJList;
    }

    public ProductController getProductController() {
        return productController;
    }
}
