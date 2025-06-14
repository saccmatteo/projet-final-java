package businessLogic;

import dataAccess.SingletonConnection;
import interfaces.ConnectionDataAccess;



public class ConnectionManager {
    private ConnectionDataAccess dao;

    public ConnectionManager() {
        setConnection(new SingletonConnection());
    }

    public void setConnection(SingletonConnection dao) {
        this.dao = dao;
    }

    public void closeConnection() {
        dao.closeConnection();
    }
}
