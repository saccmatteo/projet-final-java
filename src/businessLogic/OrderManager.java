package businessLogic;

import dataAccess.OrderDBAccess;
import model.Order;
import interfaces.OrderDataAccess;
import model.PaymentMethod;

import java.time.LocalDate;
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

    public void deleteOrder(Integer commandId) {
        dao.deleteOrder(commandId);
    }

    public void updateClosedOrder(LocalDate paymentDate, Integer commandId, PaymentMethod method) {
        dao.updateClosedOrder(paymentDate, commandId, method);
    }

    public void updateOrder(Order order) {
        dao.updateOrder(order);
    }
}
