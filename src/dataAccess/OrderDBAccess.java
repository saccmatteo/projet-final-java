package dataAccess;

import exceptions.DAOException;
import interfaces.OrderDataAccess;
import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {
    private ArrayList<Order> orders;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<Order> getAllOrders() throws DAOException {
        orders = new ArrayList<>();
        try {
            sqlInstruction =
                    "SELECT `order`.*, user.last_name, user.first_name " +
                    "FROM `order` JOIN user ON `order`.user_id = user.id " +
                    "WHERE `order`.status_label = ? " +
                    "Order by `order`.order_date DESC, `order`.id DESC";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, OrderStatus.IN_PROGRESS.getLabel());
            data = preparedStatement.executeQuery();

            try {
                while (data.next()) {
                    Order newOrder = new Order(
                            data.getInt("id"),
                            data.getDate("order_date").toLocalDate(),
                            CrudUtils.getNullableDate(data, "payment_date"), //si c'est en cours en gros cv etre null car pas encore payé
                            CrudUtils.getNullableInt(data, "discount_percentage"),
                            CrudUtils.getNullableString(data, "comment"),
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
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public int createOrder(Order order) throws DAOException {
        try {
            sqlInstruction = "INSERT INTO `order`(order_date, payment_date, discount_percentage, comment, is_happy_hour, status_label, user_id, payment_method_label) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDate(1, Date.valueOf(order.getDate()));
            CrudUtils.setNullableDate(preparedStatement, 2, order.getPaymentDate());
            CrudUtils.setNullableInt(preparedStatement, 3, order.getDiscountPercentage());
            CrudUtils.setNullableString(preparedStatement, 4, order.getComment());
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
            throw new DAOException(e, "Erreur lors de la création de la commande");
        }
        return -1;
    }

    public void deleteOrder(Integer orderId) throws DAOException {
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
            throw new DAOException(e, "Erreur lors de la suppression de la commande");
        }
    }
    public void updateClosedOrder(Integer orderId, PaymentMethod method, LocalDate paymentDate) throws DAOException {
        try {
            sqlInstruction = "UPDATE `order` SET status_label = ?, payment_method_label = ?, payment_date = ? WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, OrderStatus.COMPLETED.toString());
            preparedStatement.setString(2, method.getLabel());
            preparedStatement.setDate(3, Date.valueOf(paymentDate));
            preparedStatement.setInt(4, orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e, "Erreur lors de la finalisation de la commande");
        }
    }

    public void updateOrder(Order order) throws DAOException {
        try {
            sqlInstruction = "UPDATE `order` SET order_date = ?, payment_date = ?, discount_percentage = ?, comment = ?, is_happy_hour = ?, status_label = ?, user_id = ?, payment_method_label = ? WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setDate(1, Date.valueOf(order.getDate()));
            CrudUtils.setNullableDate(preparedStatement, 2, order.getPaymentDate());
            CrudUtils.setNullableInt(preparedStatement, 3, order.getDiscountPercentage());
            CrudUtils.setNullableString(preparedStatement, 4, order.getComment());
            preparedStatement.setBoolean(5, order.getHappyHour());
            preparedStatement.setString(6, order.getStatusLabel());
            preparedStatement.setInt(7, order.getUserId());
            preparedStatement.setString(8, order.getPaymentMethodLabel());
            preparedStatement.setInt(9, order.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e, "Erreur lors de la mise à jour de la commande");
        }
    }
}
