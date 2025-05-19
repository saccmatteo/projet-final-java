package businessLogic;

import dataAccess.OrderDBAccess;
import model.Order;
import interfaces.OrderDataAccess;
import model.PaymentMethod;

import java.util.ArrayList;

public class OrderManager {
    private OrderDataAccess dao;

    public OrderManager() {
        setDao(new OrderDBAccess());
    }

    public void setDao(OrderDBAccess orderDBAccess) {
        this.dao = orderDBAccess;
    }

    public ArrayList<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    public int createOrder(Order order) {
        return dao.createOrder(order);
    }

    public void deleteOrder(int commandId) {
        dao.deleteOrder(commandId);
    }

    public void updateClosedOrder(int commandId, PaymentMethod method) {
        dao.updateClosedOrder(commandId, method);
    }

    public void updateOrder(Order order) {
        dao.updateOrder(order);
    }
}
