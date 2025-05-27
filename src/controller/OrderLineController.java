package controller;

import businessLogic.OrderLineManager;
import exceptions.DAOException;
import model.OrderLine;

public class OrderLineController {
    private OrderLineManager manager;

    public OrderLineController() {
        setManager(new OrderLineManager());
    }

    public void setManager(OrderLineManager manager) {
        this.manager = manager;
    }

    public Double getTotalPriceOrderLine(Integer idOrder) throws DAOException{
        return manager.getTotalPriceOrderLine(idOrder);
    }

    public void createOrderLine(OrderLine orderLine, Integer orderId) throws DAOException {
        manager.createOrderLine(orderLine, orderId);
    }
}