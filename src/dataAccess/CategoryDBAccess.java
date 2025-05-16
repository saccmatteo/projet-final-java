package dataAccess;

import interfaces.CategoryDataAccess;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDBAccess implements CategoryDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        try {
            sqlInstruction = "SELECT * FROM category";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                categories.add(data.getString("label"));
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return categories;
    }
}
