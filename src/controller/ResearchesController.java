package controller;

import businessLogic.AlcoholicDrinkManager;
import businessLogic.OrderInfosManager;
import businessLogic.ProductUnderThresholdManager;
import model.AlcoholicDrinksInfos;
import model.OrderInfos;
import model.ProductsUnderThreshold;

import java.time.LocalDate;
import java.util.ArrayList;

public class ResearchesController {
    AlcoholicDrinkManager alcoholicDrinkManager;
    ProductUnderThresholdManager productUnderThresholdManager;
    OrderInfosManager orderInfosManager;

    public ResearchesController() {
        this.alcoholicDrinkManager = new AlcoholicDrinkManager();
        this.productUnderThresholdManager = new ProductUnderThresholdManager();
        this.orderInfosManager = new OrderInfosManager();
    }

    public ArrayList<AlcoholicDrinksInfos> getAlcDrinksBeforeDate(LocalDate date) {
        return alcoholicDrinkManager.getAlcDrinksBeforeDate(date);
    }

    public ArrayList<ProductsUnderThreshold> getProductsUnderThreshold(Integer threshold) {
        return productUnderThresholdManager.getAllProductsUnderThreshold(threshold);
    }

    public ArrayList<OrderInfos> getAllOrdersInfos(Integer productId) {
        return orderInfosManager.getAllOrdersInfos(productId);
    }
}
