package businessLogic;

import dataAccess.OrderLineDBAccess;
import interfaces.OrderLineDataAccess;
import model.OrderLine;

public class OrderLineManager {
    private OrderLineDataAccess dao;

    public OrderLineManager() {
        setDao(new OrderLineDBAccess());
    }

    public void setDao(OrderLineDBAccess dao) {
        this.dao = dao;
    }

    public Double getTotalPriceOrderLine(Integer idOrder){
        return dao.getTotalPriceOrderLine(idOrder);
    }
    public void createOrderLine(OrderLine orderLine, Integer orderId){
        dao.createOrderLine(orderLine, orderId);
    }

    public void updateOrderLine(Integer newQuantity, Integer orderId, Integer productId) {
        dao.updateOrderLine(newQuantity, orderId, productId);
    }
}