package controller;

import businessLogic.ProductManager;
import model.Product;
import java.util.ArrayList;

public class ProductController {
    private ProductManager manager;

    public ProductController () {
        setManager(new ProductManager());
    }

    public Double getAverageProductSelledByMonth(Integer idProduct) {
        return manager.getAverageProductSelledByMonth(idProduct);
    }

    public ArrayList<Product> getAllProducts() {
        return manager.getAllProducts();
    }

    public void setManager(ProductManager manager) {
        this.manager = manager;
    }

    public void createProduct(Product product) {
        manager.createProduct(product);
    }

    public void deleteProduct(int productId) {
        manager.deleteProduct(productId);
    }

    public void updateProduct(Product product) {
        manager.updateProduct(product);
    }
}
