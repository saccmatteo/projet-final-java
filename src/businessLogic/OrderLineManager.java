package businessLogic;

import dataAccess.OrderLineDBAccess;
import exceptions.DAOException;
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

    public Double getTotalPriceOrderLine(Integer idOrder) throws DAOException {
        return dao.getTotalPriceOrderLine(idOrder);
    }
    public void createOrderLine(OrderLine orderLine, Integer orderId) throws DAOException {
        dao.createOrderLine(orderLine, orderId);
    }
}