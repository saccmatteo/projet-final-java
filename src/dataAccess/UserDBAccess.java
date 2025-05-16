package dataAccess;

import interfaces.UserDataAccess;
import model.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDBAccess implements UserDataAccess {
    private ArrayList<User> users;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try{
            sqlInstruction = "SELECT id, last_name, first_name FROM user";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                User newUser = new User(
                        data.getInt("id"),
                        data.getString("last_name"),
                        data.getString("first_name"));
                users.add(newUser);
            }
        }
        catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        return users;
    }
}
