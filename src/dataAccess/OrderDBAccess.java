package dataAccess;

import interfaces.OrderDataAccess;
import model.Order;
import model.PaymentMethod;
import model.User;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {
    private ArrayList<Order> orders;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<Order> getAllOrders() {
        orders = new ArrayList<>();
        try {
            sqlInstruction =
                    "SELECT `order`.*, user.last_name, user.first_name " +
                    "FROM `order` JOIN user ON `order`.user_id = user.id " +
                    "WHERE `order`.status_label = 'En cours'" +
                    "Order by `order`.order_date DESC, `order`.id DESC";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                Order newOrder = new Order(
                        data.getInt("id"),
                        data.getDate("order_date").toLocalDate(),
                        crudUtils.getNullableDate(data, "payment_date"), //si c'est en cours en gros cv etre null car pas encore payé
                        crudUtils.getNullableInt(data, "discount_percentage"),
                        crudUtils.getNullableString(data, "comment"),
                        data.getBoolean("is_happy_hour"),
                        data.getString("status_label"),
                        data.getString("payment_method_label"),
                        new User(data.getInt("user_id"),
                                data.getString("user.last_name"),
                                data.getString("user.first_name")
                        )
                );
                orders.add(newOrder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public int createOrder(Order order){
        try {
            sqlInstruction = "INSERT INTO `order`(order_date, payment_date, discount_percentage, comment, is_happy_hour, status_label, user_id, payment_method_label) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDate(1, Date.valueOf(order.getDate()));
            crudUtils.setNullableDate(preparedStatement, 2, order.getPaymentDate());
            crudUtils.setNullableInt(preparedStatement, 3, order.getDiscountPercentage());
            crudUtils.setNullableString(preparedStatement, 4, order.getComment());
            preparedStatement.setBoolean(5, order.getHappyHour());
            preparedStatement.setString(6, order.getStatusLabel());
            preparedStatement.setInt(7, order.getUserId());
            preparedStatement.setString(8, order.getPaymentMethodLabel());
            preparedStatement.executeUpdate();

            data = preparedStatement.getGeneratedKeys(); // id de la commande
            if (data.next()) {
                int generatedId = data.getInt(1);
                // order.setId(data.getInt(1));
                return generatedId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void deleteOrder(int orderId) {
        try {
            sqlInstruction = "DElETE FROM orderline WHERE order_id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();

            sqlInstruction = "DELETE FROM `order` WHERE id = ?;";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateClosedOrder(int orderId, PaymentMethod method) {
        try {
            sqlInstruction = "UPDATE `order` SET status_label = ?, payment_method_label = ? WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, "Terminé");
            preparedStatement.setString(2, method.getLabel());
            preparedStatement.setInt(3, orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrder(Order order) {
        try {
            sqlInstruction = "UPDATE `order` SET order_date = ?, payment_date = ?, discount_percentage = ?, comment = ?, is_happy_hour = ?, status_label = ?, user_id = ?, payment_method_label = ? WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setDate(1, Date.valueOf(order.getDate()));
            crudUtils.setNullableDate(preparedStatement, 2, order.getPaymentDate());
            crudUtils.setNullableInt(preparedStatement, 3, order.getDiscountPercentage());
            crudUtils.setNullableString(preparedStatement, 4, order.getComment());
            preparedStatement.setBoolean(5, order.getHappyHour());
            preparedStatement.setString(6, order.getStatusLabel());
            preparedStatement.setInt(7, order.getUserId());
            preparedStatement.setString(8, order.getPaymentMethodLabel());
            preparedStatement.setInt(9, order.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
