package businessLogic;

import dataAccess.ProductUnderThresholdDBAccess;
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

    public ArrayList<ProductsUnderThreshold> getAllProductsUnderThreshold(Integer threshold) {
        return dao.getAllProductsUnderTreshold(threshold);
    }
}
