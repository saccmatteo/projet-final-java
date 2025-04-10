package dataAccess;

import model.OrderDataAccess;
import model.Order;
import model.User;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderDBAccess implements OrderDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    @Override
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            sqlInstruction = "SELECT `order`.*, user.last_name, user.first_name " +
                    "FROM `order` JOIN user ON `order`.user_id = user.id " +
                    "WHERE `order`.status_label = 'En cours'";
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
                        data.getString("status_label"),
                        data.getString("payment_method_label"),
                        new User(data.getInt("user_id"), data.getString("user.last_name"), data.getString("user.first_name"))
                );
                orders.add(newOrder);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return orders;
    }

    public void removeCommand(int commandId) {
        try {
            sqlInstruction = "DElETE FROM orderline WHERE order_id = ?";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, commandId);
            preparedStatement.executeUpdate();
            sqlInstruction = "DELETE FROM `order` WHERE id = ?;";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, commandId);
            preparedStatement.executeUpdate();

            Iterator<Order> iterator = getAllOrders().iterator();
            while (iterator.hasNext()) {
                Order order = iterator.next();
                if (order.getId() == commandId) {
                    iterator.remove();
                }
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void updateStatus(int commandId) {
        try {
            sqlInstruction = "UPDATE `order` SET status_label = 'Terminé' WHERE id = ?";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, commandId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
