package controller;

import businessLogic.OrderManager;
import model.Order;
import model.PaymentMethod;

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

    public int createOrder(Order order) {
        return manager.createOrder(order);
    }

    public void deleteOrder(Integer commandId) {
        manager.deleteOrder(commandId);
    }

    public void updateClosedOrder(Integer commandId, PaymentMethod method) {
        manager.updateClosedOrder(commandId, method);
    }

    public void updateOrder(Order order) {
        manager.updateOrder(order);
    }
}

