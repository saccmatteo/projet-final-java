package controller;

import businessLogic.OrderInfosManager;
import model.OrderInfos;

import java.util.ArrayList;

public class OrderInfosController {
    OrderInfosManager manager;

    public OrderInfosController() {
        setManager(new OrderInfosManager());
    }

    public void setManager(OrderInfosManager manager) {
        this.manager = manager;
    }

    public ArrayList<OrderInfos> getAllOrdersInfos(int productId) {
        return manager.getAllOrdersInfos(productId);
    }
}
