package controller;

import businessLogic.OrderLineManager;
import model.OrderLine;

public class OrderLineController {
    private OrderLineManager manager;

    public OrderLineController() {
        setManager(new OrderLineManager());
    }

    public void setManager(OrderLineManager manager) {
        this.manager = manager;
    }

    public Double getTotalPriceOrderLine(Integer idOrder){
        return manager.getTotalPriceOrderLine(idOrder);
    }

    public void createOrderLine(OrderLine orderLine, Integer orderId){
        manager.createOrderLine(orderLine, orderId);
    }

//    public void updateOrderLine(Integer newQuantity, Integer orderId, Integer productId) {
//        manager.updateOrderLine(newQuantity, orderId, productId);
//    }
}