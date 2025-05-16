package interfaces;

import model.ProductsUnderThreshold;

import java.util.ArrayList;

public interface ProductUnderThresholdDataAccess {
    public ArrayList<ProductsUnderThreshold> getAllProductsUnderTreshold(int treshold);
}
