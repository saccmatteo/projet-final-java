package interfaces;

import model.Product;

import java.util.ArrayList;

public interface ProductDataAccess {
    Integer getAllProductSelled(Integer idProduct);
    ArrayList<Product> getAllProducts();
    void deleteProduct(int productId);
    void createProduct(Product product);
    void updateProduct(Product product);
}
