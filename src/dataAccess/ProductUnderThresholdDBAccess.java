package dataAccess;

import interfaces.ProductUnderThresholdDataAccess;
import model.ProductsUnderThreshold;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductUnderThresholdDBAccess implements ProductUnderThresholdDataAccess {
    String sqlInstruction;
    PreparedStatement preparedStatement;
    ResultSet data;

    @Override
    public ArrayList<ProductsUnderThreshold> getAllProductsUnderTreshold(int threshold) {
        ArrayList<ProductsUnderThreshold> productsUnderThreshold = new ArrayList<>();
        try {
            sqlInstruction =
                    "SELECT " +
                    "p.id AS product_id, " +
                    "p.label AS product_label, " +
                    "p.last_restock_date, " +
                    "s.label AS supplier_name, " +
                    "s.phone_number, " +
                    "c.label AS category_name " +
                    "FROM product p " +
                    "INNER JOIN supplier s ON p.supplier_label = s.label " +
                    "INNER JOIN category c ON p.category_label = c.label " +
                    "WHERE p.nb_in_stock < ?;";

            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, threshold);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                ProductsUnderThreshold newProductUnderThreshold = new ProductsUnderThreshold(
                        data.getInt("product_id"),
                        data.getString("product_label"),
                        data.getDate("p.last_restock_date").toLocalDate(),
                        data.getString("supplier_name"),
                        data.getString("s.phoneNumber"),
                        data.getString("category_name")
                );
                productsUnderThreshold.add(newProductUnderThreshold);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return productsUnderThreshold;
    }
}
