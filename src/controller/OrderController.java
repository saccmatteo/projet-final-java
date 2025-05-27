package controller;

import businessLogic.OrderManager;
import exceptions.DAOException;
import model.Order;
import model.OrderLine;
import model.PaymentMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private OrderManager manager;

    public OrderController() {
        setManager(new OrderManager());
    }

    public void setManager(OrderManager manager) {
        this.manager = manager;
    }

    public ArrayList<Order> getAllOrders() throws DAOException {
        return manager.getAllOrders();
    }

    public int createOrder(Order order) throws DAOException {
        return manager.createOrder(order);
    }

    public void deleteOrder(Integer commandId) throws DAOException {
        manager.deleteOrder(commandId);
    }

    public void updateClosedOrder(Integer commandId, PaymentMethod method, LocalDate paymentDate) throws DAOException {
        manager.updateClosedOrder(commandId, method, paymentDate);
    }

    public void updateOrder(Order order) throws DAOException {
        manager.updateOrder(order);
    }

    public double calculateTotalPrice(ArrayList<OrderLine> orderLines, Integer discount) {
        return manager.calculateTotalPrice(orderLines, discount);
    }
}

