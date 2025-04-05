package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection connection;

    public static Connection getInstance(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "Thang2010.TOTO");
            }
            catch (SQLException error){
                error.printStackTrace(); // Plus facile pour debug
                System.out.println("Erreur de connexion : " + error.getMessage());
            }
        }
        return connection;
    }
}

