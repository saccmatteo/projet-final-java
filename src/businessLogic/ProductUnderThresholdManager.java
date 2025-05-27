package businessLogic;

import dataAccess.ProductUnderThresholdDBAccess;
import exceptions.DAOException;
import interfaces.ProductUnderThresholdDataAccess;
import model.ProductsUnderThreshold;
import java.util.ArrayList;

public class ProductUnderThresholdManager {
    private ProductUnderThresholdDataAccess dao;

    public ProductUnderThresholdManager() {
        setDao(new ProductUnderThresholdDBAccess());
    }

    public void setDao(ProductUnderThresholdDBAccess dao) {
        this.dao = dao;
    }

    public ArrayList<ProductsUnderThreshold> getAllProductsUnderThreshold(Integer threshold) throws DAOException {
        return dao.getAllProductsUnderTreshold(threshold);
    }
}
