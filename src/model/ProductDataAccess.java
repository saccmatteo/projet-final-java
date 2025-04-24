package model;

import java.util.ArrayList;

public interface ProductDataAccess {
    ArrayList<Product> getAllProducts();
    void deleteProduct(int productId);
    void createProduct(Product product);
}
