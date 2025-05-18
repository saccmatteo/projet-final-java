package interfaces;

import model.Order;

import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders();
     Integer getLastOrderId();
     void createCommand(Order order);
     void deleteCommand(int commandId);
     void updateClosedCommand(int commandId, char method);
     void updateCommand(Order order);
}
