package controller;

import businessLogic.OrderLineManager;
import interfaces.OrderLineDataAccess;
import model.Order;
import model.OrderLine;

public class OrderLineController implements OrderLineDataAccess {
    private OrderLineManager manager;

    public OrderLineController() {
        setManager(new OrderLineManager());
    }

    public void setManager(OrderLineManager manager) {
        this.manager = manager;
    }

    public void createOrderLine(OrderLine orderLine){
        manager.createOrderLine(orderLine);
    }
}