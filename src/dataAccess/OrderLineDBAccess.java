package dataAccess;

import interfaces.OrderLineDataAccess;
import model.OrderLine;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLineDBAccess implements OrderLineDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public Double getTotalPriceOrderLine(Integer idOrder){
        Double totalPrice = 0.0;
        Double discount = 0.0;

        try {
            //récuperer la somme pour le prix
            sqlInstruction = "SELECT SUM(quantity * unit_price) AS total FROM orderLine WHERE order_id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, idOrder);
            data = preparedStatement.executeQuery();
            if (data.next()) {
                totalPrice = data.getDouble("total");
            }
            data.close();
            preparedStatement.close();

            //récuperer la discount eventuelle
            sqlInstruction = "SELECT discount_percentage FROM `order` WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, idOrder);
            data = preparedStatement.executeQuery();
            if (data.next()) {
                discount = data.getDouble("discount_percentage");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //si y a une discount alors je l'applique avant de return
        if (discount > 0) {
            totalPrice = totalPrice * (1 - discount / 100);
        }

        return totalPrice;
    }

    public void createOrderLine(OrderLine orderLine, Integer orderId){
        try{
            sqlInstruction = "INSERT INTO orderline(order_id, product_id, quantity, unit_price) VALUES(?, ?, ?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, orderLine.getProduct().getId());
            preparedStatement.setInt(3, orderLine.getQuantity());
            preparedStatement.setDouble(4, orderLine.getUnitPrice());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateOrderLine(Integer newQuantity, Integer orderId, Integer productId) {
        try {
            sqlInstruction = "UPDATE orderline SET quantity = ? WHERE order_id = ? AND product_id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setInt(3, productId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}