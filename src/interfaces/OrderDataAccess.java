package interfaces;

import model.Order;
import model.PaymentMethod;

import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders();
     int createOrder(Order order);
     void deleteOrder(Integer commandId);
     void updateClosedOrder(Integer commandId, PaymentMethod method);
     void updateOrder(Order order);
}
