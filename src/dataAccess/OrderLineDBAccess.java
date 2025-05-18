package dataAccess;

import interfaces.OrderLineDataAccess;
import model.OrderLine;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderLineDBAccess implements OrderLineDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private OrderDBAccess lastOrderId = new OrderDBAccess();

    public void createOrderLine(OrderLine orderLine){
        try{
            sqlInstruction = "INSERT INTO orderline(order_id, product_id, quantity, unit_price) VALUES(?, ?, ?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setInt(1, lastOrderId.getLastOrderId());
            preparedStatement.setInt(2, orderLine.getProduct().getId());
            preparedStatement.setInt(3, orderLine.getQuantity());
            preparedStatement.setDouble(4, orderLine.getUnitPrice());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateOrderLine(int newQuantity, int orderId, int productId) {
        try {
            sqlInstruction = "UPDATE orderline SET quantity = ? WHERE order_id = ? AND product_id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setInt(3, productId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}