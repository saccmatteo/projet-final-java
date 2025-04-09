package userInterface;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ProductPanel extends JPanel {
    private Border padding;
    private Border coloredBorder;
    private ArrayList<Product> products;
    private ProductController productController;

    public ProductPanel() {
        this.productController = new ProductController();
        this.setBorder(new EmptyBorder(150,200,150,200));
        this.setLayout(new FlowLayout());

        products = productController.getAllProducts();
        for (Product product : products) {
            JButton productButton = new JButton(product.getLabel());
            productButton.setPreferredSize(new Dimension(250, 100));
            productButton.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(productButton);
        }
    }
}
