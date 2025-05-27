package businessLogic;

import dataAccess.OrderDBAccess;
import exceptions.DAOException;
import model.Order;
import interfaces.OrderDataAccess;
import model.OrderLine;
import model.PaymentMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private OrderDataAccess dao;

    public OrderManager() {
        setDao(new OrderDBAccess());
    }

    public void setDao(OrderDBAccess orderDBAccess) {
        this.dao = orderDBAccess;
    }

    public ArrayList<Order> getAllOrders() throws DAOException {
        return dao.getAllOrders();
    }

    public int createOrder(Order order) throws DAOException{
        return dao.createOrder(order);
    }

    public void deleteOrder(Integer commandId) throws DAOException{
        dao.deleteOrder(commandId);
    }

    public void updateClosedOrder(Integer commandId, PaymentMethod method, LocalDate paymentDate) throws DAOException{
        dao.updateClosedOrder(commandId, method, paymentDate);
    }

    public void updateOrder(Order order) throws DAOException {
        dao.updateOrder(order);
    }

    public double calculateTotalPrice(ArrayList<OrderLine> orderLines, Integer discount) {
        double total = 0.0;
        for (OrderLine ol : orderLines) {
            total += ol.getUnitPrice() * ol.getQuantity();
        }

        if (discount != null && discount > 0 && discount <= 100) {
            total = total * (1 - discount / 100.0);
        }

        return total;
    }
}
