package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection connection;

    // GETTERS
    public static Connection getInstance(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "Thang2010.TOTO");
            }
            catch (SQLException error){
                error.getMessage();
            }
        }
        return connection;
    }
}

