package controller;

import businessLogic.ConnectionManager;

public class ConnectionController {
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
