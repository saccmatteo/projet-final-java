package businessLogic;

import DataAccess.*;
import model.User;

import java.util.ArrayList;

public class UserManager {
    private UserDataAccess dao;

    // CONSTRUCTORS
    public UserManager() {
        setDAO(new UserDBAccess());
    }

    // SETTERS
    public void setDAO(UserDBAccess dao) {
        this.dao = dao;
    }

    public ArrayList<User> getAllUsers() {
        return dao.getAllUsers(); // à mettre dans une variable si l'on veut modifier
    }

//    public void addUser(User user) {
//        if (user != null) {
//            //dao.addUser --> A voir si on en a besoin
//        }
//    }
}
