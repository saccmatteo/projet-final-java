package interfaces;

import model.OrderLine;

public interface OrderLineDataAccess {
    void createOrderLine(OrderLine orderLine, int orderId);
    void updateOrderLine(int newQuantity, int orderId, int productId);
}