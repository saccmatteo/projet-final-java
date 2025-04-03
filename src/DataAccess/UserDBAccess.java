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

    // GETTERS
    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    // METHODES
    public void FillInLists() throws AllUsersException{
        try{
            sqlInstruction = "SELECT id, last_name, first_name FROM user";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                // Pas besoin de verifier car NOT NULL

                // ?????????
            }
        }
        catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }

    }
}
