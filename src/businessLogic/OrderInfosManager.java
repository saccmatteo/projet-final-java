package businessLogic;

import dataAccess.OrderInfosDBAccess;
import interfaces.OrderInfosDataAccess;
import model.OrderInfos;

import java.util.ArrayList;

public class OrderInfosManager {
    OrderInfosDataAccess dao;

    public OrderInfosManager() {
        setDao(new OrderInfosDBAccess());
    }

    public void setDao(OrderInfosDataAccess dao) {
        this.dao = dao;
    }

    public ArrayList<OrderInfos> getAllOrdersInfos(int productId) {
        return dao.getAllOrdersInfos(productId);
    }
}
