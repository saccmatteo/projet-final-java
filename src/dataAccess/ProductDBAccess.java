package dataAccess;
import model.Order;
import model.Product;
import model.ProductDataAccess;

import java.sql.Date;
import java.time.LocalDate;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductDBAccess implements ProductDataAccess {
    private ArrayList<Product> products;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
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
        return products;
    }

    public void deleteProduct(int productId) {
        try {
            sqlInstruction = "DELETE FROM product WHERE id = ?";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            Iterator<Product> iterator = getAllProducts().iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (product.getId() == productId) {
                    iterator.remove();
                }
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    public void createProduct(Product product) {
        try{
            // Product
            sqlInstruction = "INSERT INTO product VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getLabel());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getNbInStock());
            preparedStatement.setInt(5, product.getMinTreshold());
            preparedStatement.setBoolean(6, product.getGlutenFree());
            preparedStatement.setDouble(7, product.getAlcoholPercentage());
            preparedStatement.setString(10, product.getDescription());
            preparedStatement.setString(11, product.getSupplierLabel());
            preparedStatement.setString(12, product.getCategoryLabel());

                // DATE
            preparedStatement.setDate(8, Date.valueOf(product.getDistributionDate()));
            preparedStatement.setDate(9, Date.valueOf(product.getLastRestockDate()));

            preparedStatement.executeUpdate();

            // Supplier
            sqlInstruction = "INSERT INTO supplier VALUES (?, ?)";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, product.getSupplierLabel());
            preparedStatement.setInt(2, product.getSupplierPhoneNumber());

            preparedStatement.executeUpdate();

            // Category
            sqlInstruction = "INSERT INTO category VALUES (?)";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, product.getCategoryLabel());
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
