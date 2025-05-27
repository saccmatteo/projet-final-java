package exceptions;

import java.sql.SQLException;

public class DAOException extends Exception {
    SQLException sqlException;
    public DAOException (SQLException sqlException, String message) {
        super(message);
        setSqlException(sqlException);
    }

    public void setSqlException(SQLException sqlException) {
        this.sqlException = sqlException;
    }
}
