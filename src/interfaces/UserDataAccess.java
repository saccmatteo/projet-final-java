package interfaces;

import exceptions.DAOException;
import model.User;

import java.util.ArrayList;

public interface UserDataAccess {
    ArrayList<User> getAllUsers() throws DAOException;
}
