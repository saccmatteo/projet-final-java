package interfaces;

import exceptions.DAOException;
import model.Order;
import model.PaymentMethod;

import java.time.LocalDate;
import java.util.ArrayList;

public interface OrderDataAccess {
     ArrayList<Order> getAllOrders() throws DAOException;
     int createOrder(Order order) throws DAOException;
     void deleteOrder(Integer commandId) throws DAOException;
     void updateClosedOrder(Integer commandId, PaymentMethod method, LocalDate paymentDate) throws DAOException;
     void updateOrder(Order order) throws DAOException;
}
