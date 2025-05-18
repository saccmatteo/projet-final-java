package dataAccess;

import interfaces.OrderLineDataAccess;
import model.OrderLine;

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
}