package controller;

import businessLogic.OrderManager;
import model.Order;

import java.util.ArrayList;

public class OrderController {
    private OrderManager manager;

    public OrderController() {
        setManager(new OrderManager());
    }

    public void setManager(OrderManager manager) {
        this.manager = manager;
    }

    public ArrayList<Order> getAllOrders() {
        return manager.getAllOrders();
    }
    public void removeCommand(int commandId) {
        manager.removeCommand(commandId);
    }
    public void updateStatus(int commandId) {
        manager.updateStatus(commandId);
    }
}

