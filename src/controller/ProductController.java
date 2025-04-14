package controller;

import businessLogic.ProductManager;
import model.Product;
import java.util.ArrayList;

public class ProductController {
    private ProductManager manager;

    public ProductController () {
        setManager(new ProductManager());
    }

    public ArrayList<Product> getAllProducts() {
        return manager.getAllProducts();
    }

    public void setManager(ProductManager manager) {
        this.manager = manager;
    }

    public void addProduct(Product product) {
        manager.addProduct(product);
    }
}
