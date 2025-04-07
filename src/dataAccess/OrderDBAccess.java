package dataAccess;

import businessLogic.OrderDataAccess;
import model.Order;
import model.PaymentMethod;
import model.Status;
import model.User;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {
    private ArrayList<Order> orders = new ArrayList<>();
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public OrderDBAccess() {
        try {
            sqlInstruction = "SELECT `order`.*, user.last_name, user.first_name " +
                    "FROM `order` JOIN user ON `order`.user_id = user.id " +
                    "WHERE `order`.status_label = 'En cours'";
            // sqlInstruction = "SELECT `order`.id, `order`.order_date, `order`.user_id, user.last_name, user.first_name FROM `order` JOIN user ON `order`.user_id = user.id WHERE `order`.status_label = 'En cours'";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                Order newOrder = new Order(
                        data.getInt("id"),
                        data.getDate("order_date").toLocalDate(),
                        data.getDate("payment_date").toLocalDate(),
                        data.getInt("discount_percentage"),
                        data.getString("comment"),
                        data.getBoolean("is_happy_hour"),
                        new Status(data.getString("status_label")),
                        new PaymentMethod(data.getString("payment_method_label")),
                        new User(data.getInt("user_id"), data.getString("user.last_name"), data.getString("user.first_name"))
                );
                orders.add(newOrder);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return orders;
    }
}
