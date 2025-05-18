package businessLogic;

import dataAccess.OrderLineDBAccess;
import interfaces.OrderLineDataAccess;
import model.Order;
import model.OrderLine;

public class OrderLineManager implements OrderLineDataAccess {
    private OrderLineDataAccess dao;

    public OrderLineManager() {
        setDao(new OrderLineDBAccess());
    }

    public void setDao(OrderLineDataAccess dao) {
        this.dao = dao;
    }

    public void createOrderLine(OrderLine orderLine){
        dao.createOrderLine(orderLine);
    }
}