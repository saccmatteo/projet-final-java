package businessLogic;

import dataAccess.ProductDBAccess;
import model.Product;
import model.ProductDataAccess;

import java.util.ArrayList;

public class ProductManager {
    private ProductDataAccess dao;
    public ProductManager() {
        setDao(new ProductDBAccess());
    }

    public void setDao(ProductDBAccess productDBAccess) {
        this.dao = productDBAccess;
    }

    public ArrayList<Product> getAllProducts (){
        return dao.getAllProducts();
    }
}
