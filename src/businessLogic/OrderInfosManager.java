package businessLogic;

import dataAccess.OrderInfosDBAccess;
import exceptions.DAOException;
import interfaces.OrderInfosDataAccess;
import model.OrderInfos;

import java.util.ArrayList;

public class OrderInfosManager {
    private OrderInfosDataAccess dao;

    public OrderInfosManager() {
        setDao(new OrderInfosDBAccess());
    }

    public void setDao(OrderInfosDBAccess dao) {
        this.dao = dao;
    }

    public ArrayList<OrderInfos> getAllOrdersInfos(Integer productId) throws DAOException {
        return dao.getAllOrdersInfos(productId);
    }
}
