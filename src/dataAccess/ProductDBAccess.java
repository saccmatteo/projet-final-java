package dataAccess;

import model.Product;
import interfaces.ProductDataAccess;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;

public class ProductDBAccess implements ProductDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public Integer getAllProductSelledLast6Months(Integer idProduct){
        Integer totalProductSelled = 0;
        try{
            sqlInstruction =
                    "SELECT SUM(ol.quantity) AS totalQuantity " +
                            "FROM orderline ol " +
                            "INNER JOIN `order` o ON ol.order_id = o.id " +
                            "WHERE ol.product_id = ? " +
                            "AND o.order_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, idProduct);
            data = preparedStatement.executeQuery();

            if (data.next()) {
                totalProductSelled = data.getInt("totalQuantity");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return totalProductSelled;
    }


    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            sqlInstruction = "SELECT product.*, supplier.phone_number FROM product INNER JOIN supplier ON supplier.label = product.supplier_label INNER JOIN category ON category.label = product.category_label";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                Product newProduct = new Product(
                        data.getInt("id"),
                        data.getString("label"),
                        data.getDouble("price"),
                        data.getInt("nb_in_stock"),
                        data.getInt("min_treshold"),
                        data.getBoolean("is_gluten_free"),
                        CrudUtils.getNullableDouble(data, "alcohol_percentage"),
                        data.getDate("distribution_date").toLocalDate(),
                        data.getDate("last_restock_date").toLocalDate(),
                        CrudUtils.getNullableString(data, "description"),
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
    public void deleteProduct(Integer productId) {
        try {
            sqlInstruction = "DELETE FROM product WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    public void createProduct(Product product) {
        try{
            // Category
            sqlInstruction = "INSERT IGNORE INTO category VALUES (?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, product.getCategoryLabel());
            preparedStatement.executeUpdate();

            // Supplier
            sqlInstruction = "INSERT IGNORE INTO supplier VALUES (?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, product.getSupplierLabel());
            preparedStatement.setInt(2, product.getSupplierPhoneNumber());
            preparedStatement.executeUpdate();

            // Product&é
            sqlInstruction = "INSERT INTO product(label, price, nb_in_stock, min_treshold, is_gluten_free, alcohol_percentage, distribution_date, last_restock_date, description, category_label, supplier_label) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, product.getLabel());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getNbInStock());
            preparedStatement.setInt(4, product.getMinTreshold());
            preparedStatement.setBoolean(5, product.getGlutenFree());
            CrudUtils.setNullableDouble(preparedStatement, 6, product.getAlcoholPercentage());
            preparedStatement.setDate(7, Date.valueOf(product.getDistributionDate()));
            preparedStatement.setDate(8, Date.valueOf(product.getLastRestockDate()));
            CrudUtils.setNullableString(preparedStatement, 9, product.getDescription());
            preparedStatement.setString(10, product.getCategoryLabel());
            preparedStatement.setString(11, product.getSupplierLabel());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void updateProduct(Product product) {
        try {
            sqlInstruction = "UPDATE product SET label = ?, price = ?, nb_in_stock = ?, min_treshold = ?, is_gluten_free = ?, alcohol_percentage = ?, distribution_date = ?, last_restock_date = ?, description = ?, category_label = ?, supplier_label = ? WHERE id = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, product.getLabel());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getNbInStock());
            preparedStatement.setInt(4, product.getMinTreshold());
            preparedStatement.setBoolean(5, product.getGlutenFree());
            CrudUtils.setNullableDouble(preparedStatement, 6, product.getAlcoholPercentage());
            preparedStatement.setDate(7, Date.valueOf(product.getDistributionDate()));
            preparedStatement.setDate(8, Date.valueOf(product.getLastRestockDate()));
            CrudUtils.setNullableString(preparedStatement, 9, product.getDescription());
            preparedStatement.setString(10, product.getCategoryLabel());
            preparedStatement.setString(11, product.getSupplierLabel());
            preparedStatement.setInt(12, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
