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

    public Integer getLastOrderId() {
        return manager.getLastOrderId();
    }

    public void createCommand(Order order) {
        manager.createCommand(order);
    }

    public void deleteCommand(int commandId) {
        manager.deleteCommand(commandId);
    }

    public void updateClosedCommand(int commandId, char method) {
        manager.updateClosedCommand(commandId, method);
    }

    public void updateCommand(Order order) {
        manager.updateCommand(order);
    }
}

