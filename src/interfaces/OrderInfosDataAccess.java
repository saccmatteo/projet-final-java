package interfaces;

import exceptions.DAOException;
import model.OrderInfos;
import model.Product;

import java.util.ArrayList;

public interface OrderInfosDataAccess {
    public ArrayList<OrderInfos> getAllOrdersInfos(Integer productId) throws DAOException;
}
