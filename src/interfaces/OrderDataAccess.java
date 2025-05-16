package interfaces;

import model.Order;
import model.OrderLine;

import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders();
     void createCommand(Order order);
     void deleteCommand(int commandId);
     void updateCommand(int commandId, char method);
}
