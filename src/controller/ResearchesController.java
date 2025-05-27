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
    private AlcoholicDrinkManager alcoholicDrinkManager;
    private ProductUnderThresholdManager productUnderThresholdManager;
    private OrderInfosManager orderInfosManager;

    public ResearchesController() {
        setAlcoholicDrinkManager(new AlcoholicDrinkManager());
        setProductUnderThresholdManager(new ProductUnderThresholdManager());
        setOrderInfosManager(new OrderInfosManager());
    }

    // SETTERS
    public void setAlcoholicDrinkManager(AlcoholicDrinkManager alcoholicDrinkManager) {
        this.alcoholicDrinkManager = alcoholicDrinkManager;
    }
    public void setProductUnderThresholdManager(ProductUnderThresholdManager productUnderThresholdManager) {
        this.productUnderThresholdManager = productUnderThresholdManager;
    }
    public void setOrderInfosManager(OrderInfosManager orderInfosManager) {
        this.orderInfosManager = orderInfosManager;
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
