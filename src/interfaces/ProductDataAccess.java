package interfaces;

import model.Product;

import java.util.ArrayList;

public interface ProductDataAccess {
    Integer getAllProductSelledLast6Months(Integer idProduct);
    ArrayList<Product> getAllProducts();
    void deleteProduct(int productId);
    void createProduct(Product product);
    void updateProduct(Product product);
    void updateStock(Product product, int newStock);
}
