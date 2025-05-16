package controller;

import businessLogic.ProductUnderThresholdManager;
import model.ProductsUnderThreshold;

import java.util.ArrayList;

public class ProductUnderThresholdController {
    private ProductUnderThresholdManager manager;

    public ProductUnderThresholdController() {
        setManager(new ProductUnderThresholdManager());
    }

    public void setManager(ProductUnderThresholdManager manager) {
        this.manager = manager;
    }

    public ArrayList<ProductsUnderThreshold> getProductsUnderThreshold(int threshold) {
        return manager.getAllProductsUnderThreshold(threshold);
    }
}
