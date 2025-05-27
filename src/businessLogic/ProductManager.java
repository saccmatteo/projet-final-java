package businessLogic;

import dataAccess.ProductDBAccess;
import exceptions.DAOException;
import model.Product;
import interfaces.ProductDataAccess;

import java.util.ArrayList;

public class ProductManager {
    private ProductDataAccess dao;

    public ProductManager() {
        setDao(new ProductDBAccess());
    }

    public void setDao(ProductDBAccess dao) {
        this.dao = dao;
    }

    public Double calcAverageSalesLast6Months(Integer productId) throws DAOException {
        return dao.getAllProductSelledLast6Months(productId) / 6.0;
    }

    public ArrayList<Product> getAllProducts () throws DAOException {
        return dao.getAllProducts();
    }

    public void createProduct(Product product) throws DAOException  {
        dao.createProduct(product);
    }

    public void deleteProduct(Integer productId) throws DAOException {
        dao.deleteProduct(productId);
    }

    public void updateProduct(Product product) throws DAOException {
        dao.updateProduct(product);
    }
    public void updateStock(Product product, Integer newStock) throws DAOException {dao.updateStock(product, newStock);}
}
