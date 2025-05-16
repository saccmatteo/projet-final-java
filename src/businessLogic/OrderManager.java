package businessLogic;

import dataAccess.OrderDBAccess;
import model.Order;
import interfaces.OrderDataAccess;
import model.OrderLine;

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

    public void createCommand(Order order) {
        dao.createCommand(order);
    }

    public void deleteCommand(int commandId) {
        dao.deleteCommand(commandId);
    }

    public void updateCommand(int commandId, char method) {
        dao.updateCommand(commandId, method);
    }
}
