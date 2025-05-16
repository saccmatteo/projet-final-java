package dataAccess;

import interfaces.OrderDataAccess;
import model.Order;
import model.OrderLine;
import model.User;
import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {
    private ArrayList<Order> orders;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public OrderDBAccess(){

    }

    public ArrayList<Order> getAllOrders() {
        orders = new ArrayList<>();
        try {
            sqlInstruction = "SELECT `order`.*, user.last_name, user.first_name " +
                    "FROM `order` JOIN user ON `order`.user_id = user.id " +
                    "WHERE `order`.status_label = 'En cours'";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
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
            System.out.println(e.getMessage());
        }

        return orders;
    }

    public Integer getLastOrderId() {
        Integer id = -1;
        try{
            sqlInstruction = "SELECT * " +
                             "FROM `order` " +
                             "ORDER BY id DESC " +
                             "LIMIT 1";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            System.out.println("=======\nlast order id " + data.getInt("id") +"\n=======");
            if (data.next()) {
                id = data.getInt("id");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public void createCommand(Order order){
        try{
            sqlInstruction = "INSERT INTO `order`(order_date, payment_date, discount_percentage, comment, is_happy_hour, status_label, user_id, payment_method_label) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setDate(1, Date.valueOf(order.getDate()));
            System.out.println("id ajoute");
            preparedStatement.setDate(2, Date.valueOf(order.getPaymentDate()));
            preparedStatement.setInt(3, order.getDiscountPercentage());
            preparedStatement.setString(4, order.getComment());
            preparedStatement.setBoolean(5, order.getHappyHour());
            preparedStatement.setString(6, order.getStatusLabel());
            preparedStatement.setInt(7, order.getUserId());
            preparedStatement.setString(8, order.getPaymentMethodLabel());
            System.out.println("Finitop");
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteCommand(int commandId) {
        try {
            sqlInstruction = "DElETE FROM orderline WHERE order_id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, commandId);
            preparedStatement.executeUpdate();

            sqlInstruction = "DELETE FROM `order` WHERE id = ?;";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, commandId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateCommand(int commandId, char method) {
        try {
            sqlInstruction = "UPDATE `order` SET status_label = 'Terminé' WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, commandId);
            preparedStatement.executeUpdate();
            if (method == 'c') {
                sqlInstruction = "UPDATE `order` SET payment_method_label = 'Carte bancaire' WHERE id = ?";
                preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
                preparedStatement.setInt(1, commandId);
                preparedStatement.executeUpdate();
            }
            else {
                sqlInstruction = "UPDATE `order` SET payment_method_label = 'Espèces' WHERE id = ?";
                preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
                preparedStatement.setInt(1, commandId);
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
