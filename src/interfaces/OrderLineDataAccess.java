package interfaces;

import model.OrderLine;

public interface OrderLineDataAccess {
    void createOrderLine(OrderLine orderLine);
    void updateOrderLine(int newQuantity, int orderId, int productId);
}