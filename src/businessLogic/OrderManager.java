package businessLogic;

import dataAccess.OrderDBAccess;
import model.Order;
import interfaces.OrderDataAccess;

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

    public Integer getLastOrderId() {
        return dao.getLastOrderId();
    }

    public void createCommand(Order order) {
        dao.createCommand(order);
    }

    public void deleteCommand(int commandId) {
        dao.deleteCommand(commandId);
    }

    public void updateClosedCommand(int commandId, char method) {
        dao.updateClosedCommand(commandId, method);
    }

    public void updateCommand(Order order) {
        dao.updateCommand(order);
    }
}
