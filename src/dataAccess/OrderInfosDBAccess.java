package dataAccess;

import interfaces.OrderInfosDataAccess;
import model.OrderInfos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderInfosDBAccess implements OrderInfosDataAccess {
    String sqlInstruction;
    PreparedStatement preparedStatement;
    ResultSet data;

    public ArrayList<OrderInfos> getAllOrdersInfos(Integer productID){
        ArrayList<OrderInfos> ordersInfos = new ArrayList<>();
        try {
            sqlInstruction =
                    "SELECT " +
                    "u.function_label, " +
                    "u.first_name, " +
                    "u.last_name, " +
                    "ol.quantity, " +
                    "o.order_date, " +
                    "o.status_label " +
                    "FROM `order` o " +
                    "JOIN orderLine ol ON o.id = ol.order_id " +
                    "JOIN `user` u ON o.user_id = u.id " +
                    "WHERE ol.product_id = ?; ";

            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, productID);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                OrderInfos orderInfos = new OrderInfos(
                        data.getDate("o.order_date").toLocalDate(),
                        data.getString("o.status_label"),
                        data.getInt("ol.quantity"),
                        data.getString("u.function_label"),
                        data.getString("u.first_name"),
                        data.getString("u.last_name")
                );
                ordersInfos.add(orderInfos);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ordersInfos;
    }
}
