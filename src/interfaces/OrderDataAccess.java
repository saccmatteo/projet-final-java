package interfaces;

import model.Order;
import model.PaymentMethod;

import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders();
     int createOrder(Order order);
     void deleteOrder(int commandId);
     void updateClosedOrder(int commandId, PaymentMethod method);
     void updateOrder(Order order);
}
