package businessLogic;

import dataAccess.ProductDBAccess;
import model.Product;
import interfaces.ProductDataAccess;

import java.util.ArrayList;

public class ProductManager {
    private ProductDataAccess dao;

    public ProductManager() {
        setDao(new ProductDBAccess());
    }

    public void setDao(ProductDBAccess productDBAccess) {
        this.dao = productDBAccess;
    }

    public Integer getAllProductSelledLast6Months(Integer idProduct) {
        return dao.getAllProductSelledLast6Months(idProduct);
    }

    public ArrayList<Product> getAllProducts (){
        return dao.getAllProducts();
    }

    public void createProduct(Product product) {
        dao.createProduct(product);
    }

    public void deleteProduct(int productId) {
        dao.deleteProduct(productId);
    }

    public void updateProduct(Product product) {
        dao.updateProduct(product);
    }
    public void updateStock(Product product, int newStock) {dao.updateStock(product, newStock);}
}
