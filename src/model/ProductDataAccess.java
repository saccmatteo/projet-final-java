package model;

import java.util.ArrayList;

public interface ProductDataAccess {
    ArrayList<Product> getAllProducts();
    void removeProduct(int productId);
    void addProduct(Product product);
}
