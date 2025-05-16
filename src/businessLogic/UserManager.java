package businessLogic;

import dataAccess.*;
import model.User;
import interfaces.UserDataAccess;

import java.util.ArrayList;

public class UserManager {
    private UserDataAccess dao;

    public UserManager() {
        setDAO(new UserDBAccess());
    }

    public void setDAO(UserDBAccess dao) {
        this.dao = dao;
    }

    public ArrayList<User> getAllUsers() {
        return dao.getAllUsers(); // à mettre dans une variable si l'on veut verifier
    }
}
