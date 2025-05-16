package interfaces;

import model.OrderInfos;
import model.Product;

import java.util.ArrayList;

public interface OrderInfosDataAccess {
    public ArrayList<OrderInfos> getAllOrdersInfos(int productId);
}
