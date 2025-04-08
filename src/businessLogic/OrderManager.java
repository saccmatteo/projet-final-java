package businessLogic;

import dataAccess.OrderDBAccess;
import model.Order;

import java.util.ArrayList;

public class OrderManager {
    private OrderDataAccess dao;
    // private ArrayList<Order> orders;

    public OrderManager() {
        setDao(new OrderDBAccess());
        // orders = getAllOrders();
    }

    public void setDao(OrderDBAccess orderDBAccess) {
        this.dao = orderDBAccess;
    }

    public ArrayList<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    public void deleteOrder(Order order) {
        getAllOrders().remove(order);
    }
}
