package businessLogic;

import dataAccess.*;
import exceptions.DAOException;
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

    public ArrayList<User> getAllUsers() throws DAOException {
        return dao.getAllUsers();
    }
}
