package interfaces;

import model.OrderLine;

public interface OrderLineDataAccess {
    void createOrderLine(OrderLine orderLine);
}