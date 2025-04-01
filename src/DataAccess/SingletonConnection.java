package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection connection;

    // CONSTRUCTORS
    private SingletonConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lps", "root", "Thang2010.TOTO");
        }
        catch (SQLException error){
            error.getMessage();
        }
    }

    // GETTERS
    public static Connection getConnection(){
        if (connection == null) {
            new SingletonConnection();
        }
        return connection;
    }
}

