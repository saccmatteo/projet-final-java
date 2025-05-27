package interfaces;

import exceptions.DAOException;
import model.OrderLine;

import java.util.ArrayList;

public interface OrderLineDataAccess {
    Double getTotalPriceOrderLine(Integer idOrder) throws DAOException;
    void createOrderLine(OrderLine orderLine, Integer orderId) throws DAOException;
}