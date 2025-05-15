package interfaces;

import model.Order;

import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders();
     void deleteCommand(int commandId);
     void updateCommand(int commandId, char method);
}
