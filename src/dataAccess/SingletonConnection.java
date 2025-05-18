package dataAccess;

import interfaces.ConnectionDataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection implements ConnectionDataAccess {
    private static Connection connection;

    public static Connection getInstance(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "Tigrou007");
            }
            catch (SQLException error){
                error.printStackTrace(); // Plus facile pour debug
                System.out.println("Erreur de connexion : " + error.getMessage());
            }
        }
        return connection;
    }

    public void closeConnection(){
        try {
            if (connection != null){
                connection.close();
            }
        }
        catch (SQLException error){
            error.printStackTrace();
        }
    }
}

