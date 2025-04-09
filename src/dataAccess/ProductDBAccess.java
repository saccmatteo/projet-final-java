package dataAccess;
import model.Product;
import model.ProductDataAccess;
import userInterface.ProductPanel;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDBAccess implements ProductDataAccess {
    private ArrayList<Product> products;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ProductDBAccess() {
        this.products = new ArrayList<>();
        try {
            sqlInstruction = "SELECT product.*, supplier.phone_number FROM product INNER JOIN supplier ON supplier.label = product.supplier_label INNER JOIN category ON category.label = product.category_label";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                Product newProduct = new Product(
                        data.getInt("id"),
                        data.getString("label"),
                        data.getDouble("price"),
                        data.getInt("nb_in_stock"),
                        data.getInt("min_treshold"),
                        data.getBoolean("is_gluten_free"),
                        data.getDouble("alcohol_percentage"),
                        data.getDate("distribution_date").toLocalDate(),
                        data.getDate("last_restock_date").toLocalDate(),
                        data.getString("description"),
                        data.getString("supplier_label"),
                        data.getInt("supplier.phone_number"),
                        data.getString("category_label")
                );
                products.add(newProduct);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }
}
