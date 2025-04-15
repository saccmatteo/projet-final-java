package model;

import model.Order;

import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders();
     void removeCommand(int commandId);
     void updateStatus(int commandId);
     void updatePaymentMethod(int commandId, char method);
}
