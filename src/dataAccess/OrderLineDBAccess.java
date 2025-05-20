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
        try{
            sqlInstruction = "SELECT * " +
                             "FROM orderline " +
                             "WHERE order_id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, idOrder);
            data = preparedStatement.executeQuery();

            while (data.next()){
                totalPrice += data.getInt("quantity") * data.getDouble("unit_price");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return totalPrice;
    }

    public void createOrderLine(OrderLine orderLine, int orderId){
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

    public void updateOrderLine(int newQuantity, int orderId, int productId) {
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