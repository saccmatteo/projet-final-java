package controller;

import businessLogic.UserManager;
import exceptions.DAOException;
import model.User;

import java.util.ArrayList;

public class UserController {
    private UserManager manager;

    public UserController() {
        setManager(new UserManager());
    }

    public void setManager(UserManager manager) {
        this.manager = manager;
    }

    public ArrayList<User> getAllUsers() throws DAOException {
        return manager.getAllUsers();
    }
}
