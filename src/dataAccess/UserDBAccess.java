package dataAccess;

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

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try{
            sqlInstruction = "SELECT * FROM user";
            preparedStatement = dataAccess.SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                User newUser = new User(
                        data.getInt("id"),
                        data.getString("last_name"),
                        data.getString("first_name"),
                        data.getString("function_Label"));
                users.add(newUser);
            }
            data.close();
            preparedStatement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }
}
