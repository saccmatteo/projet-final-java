package dataAccess;

import exceptions.DAOException;
import interfaces.UserDataAccess;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDBAccess implements UserDataAccess {
    private ArrayList<User> users;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<User> getAllUsers() throws DAOException {
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
        } catch (SQLException e){
            throw new DAOException(e, "Erreur lors de la récupération des utilisateurs");
        }
        return users;
    }
}
