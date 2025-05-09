package businessLogic;

import dataAccess.OrderDBAccess;
import model.Order;
import model.OrderDataAccess;

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

    public void deleteCommand(int commandId) {
        dao.deleteCommand(commandId);
    }

    public void updateCommand(int commandId, char method) { dao.updateCommand(commandId, method); }
}
