package controller;

import businessLogic.ConnectionManager;
import interfaces.ConnectionDataAccess;

public class ConnectionController implements ConnectionDataAccess {
    private ConnectionManager manager;

    public ConnectionController() {
        setManager(new ConnectionManager());
    }

    public void setManager(ConnectionManager manager) {
        this.manager = manager;
    }

    public void closeConnection() {
        manager.closeConnection();
    }
}
