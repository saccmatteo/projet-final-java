package interfaces;

import model.OrderLine;

import java.util.ArrayList;

public interface OrderLineDataAccess {
    Double getTotalPriceOrderLine(Integer idOrder);
    void createOrderLine(OrderLine orderLine, Integer orderId);
}