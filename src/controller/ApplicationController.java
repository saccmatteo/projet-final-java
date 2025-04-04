package controller;

import businessLogic.UserManager;
import model.User;

import java.util.ArrayList;

public class ApplicationController {
    private UserManager manager;

    public ApplicationController() {
        setManager(new UserManager());
    }

    public void setManager(UserManager manager) {
        this.manager = manager;
    }

    public ArrayList<User> getAllUsers() {
        return manager.getAllUsers();
    }
}
