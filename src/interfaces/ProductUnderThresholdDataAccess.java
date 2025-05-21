package interfaces;

import model.ProductsUnderThreshold;

import java.util.ArrayList;

public interface ProductUnderThresholdDataAccess {
    ArrayList<ProductsUnderThreshold> getAllProductsUnderTreshold(Integer treshold);
}
