package interfaces;

import exceptions.DAOException;

import java.util.ArrayList;

public interface CategoryDataAccess {
    ArrayList<String> getAllCategories() throws DAOException;
}
