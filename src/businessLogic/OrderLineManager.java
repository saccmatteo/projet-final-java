package businessLogic;

import dataAccess.OrderLineDBAccess;
import interfaces.OrderLineDataAccess;
import model.OrderLine;

public class OrderLineManager {
    private OrderLineDataAccess dao;

    public OrderLineManager() {
        setDao(new OrderLineDBAccess());
    }

    public void setDao(OrderLineDataAccess dao) {
        this.dao = dao;
    }

    public void createOrderLine(OrderLine orderLine, int orderId){
        dao.createOrderLine(orderLine, orderId);
    }

    public void updateOrderLine(int newQuantity, int orderId, int productId) {
        dao.updateOrderLine(newQuantity, orderId, productId);
    }
}