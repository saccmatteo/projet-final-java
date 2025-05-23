package controller;

import businessLogic.ProductManager;
import model.Product;
import java.util.ArrayList;

public class ProductController {
    private ProductManager manager;

    public ProductController () {
        setManager(new ProductManager());
    }

    public Integer getAllProductSelledLast6Months(Integer idProduct) {
        return manager.getAllProductSelledLast6Months(idProduct);
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
    public void updateStock(Product product, int newStock) {manager.updateStock(product, newStock);}
}
