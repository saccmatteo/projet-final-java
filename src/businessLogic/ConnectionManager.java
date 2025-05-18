package businessLogic;

import interfaces.ConnectionDataAccess;

public class ConnectionManager implements ConnectionDataAccess {
    private ConnectionDataAccess dao;

    public ConnectionManager() {
        setConnection(new ConnectionManager());
    }

    public void setConnection(ConnectionDataAccess connection) {
        this.dao = connection;
    }

    public void closeConnection() {
        dao.closeConnection();
    }
}
