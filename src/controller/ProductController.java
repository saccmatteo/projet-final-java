package controller;

import businessLogic.ProductManager;
import exceptions.DAOException;
import model.Product;
import java.util.ArrayList;

public class ProductController {
    private ProductManager manager;

    public ProductController () {
        setManager(new ProductManager());
    }

    public Double calcAverageSalesLast6Months(Integer productId) throws DAOException {
        return manager.calcAverageSalesLast6Months(productId);
    }

    public ArrayList<Product> getAllProducts() throws DAOException {
        return manager.getAllProducts();
    }

    public void setManager(ProductManager manager) {
        this.manager = manager;
    }

    public void createProduct(Product product) throws DAOException {
        manager.createProduct(product);
    }

    public void deleteProduct(Integer productId) throws DAOException {
        manager.deleteProduct(productId);
    }

    public void updateProduct(Product product) throws DAOException {
        manager.updateProduct(product);
    }
    public void updateStock(Product product, Integer newStock) throws DAOException {
        manager.updateStock(product, newStock);}
}
