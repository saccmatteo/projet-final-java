package model;

import model.User;

import java.util.ArrayList;

public interface UserDataAccess {
    ArrayList<User> getAllUsers();
}
