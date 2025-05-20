package interfaces;

import model.OrderLine;

import java.util.ArrayList;

public interface OrderLineDataAccess {
    Double getTotalPriceOrderLine(Integer idOrder);
    void createOrderLine(OrderLine orderLine, int orderId);
    void updateOrderLine(int newQuantity, int orderId, int productId);
}