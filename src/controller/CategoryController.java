package controller;

import businessLogic.CategoryManager;
import exceptions.DAOException;

import java.util.ArrayList;

public class CategoryController {
    private CategoryManager manager;

    public CategoryController() {
        setManager(new CategoryManager());
    }

    public void setManager(CategoryManager manager) {
        this.manager = manager;
    }

    public ArrayList<String> getAllCategories() throws DAOException {
        return manager.getAllCategories();
    }
}
