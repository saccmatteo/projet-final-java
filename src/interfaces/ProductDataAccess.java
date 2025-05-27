package interfaces;

import exceptions.DAOException;
import model.Product;

import java.util.ArrayList;

public interface ProductDataAccess {
    Integer getAllProductSelledLast6Months(Integer idProduct) throws DAOException;
    ArrayList<Product> getAllProducts() throws DAOException;
    void deleteProduct(Integer productId) throws DAOException;
    void createProduct(Product product) throws DAOException;
    void updateProduct(Product product) throws DAOException;
    void updateStock(Product product, Integer newStock) throws DAOException;
}
