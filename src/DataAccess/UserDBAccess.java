package DataAccess;

import businessLogic.UserDataAccess;
import model.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDBAccess implements UserDataAccess {
    private ArrayList<User> users = new ArrayList<>();
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public UserDBAccess() {
        createUsers();
    }
    // GETTERS
    @Override
    public ArrayList<User> getAllUsers() {
        return users;
    }

    // METHODES
    public void createUsers() throws AllUsersException{
        try{
            sqlInstruction = "SELECT id, last_name, first_name FROM user";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                // Pas besoin de verifier car NOT NULL
                int id = data.getInt("id");
                String lastName = data.getString("last_name");
                String firstName = data.getString("first_name");
                User newUser = new User(id, lastName, firstName);
                users.add(newUser);
            }
            System.out.println("Nombre d'utilisateurs chargés : " + users.size());
        }
        catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
}
