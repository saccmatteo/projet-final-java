package controller;

import businessLogic.CategoryManager;

import java.util.ArrayList;

public class CategoryController {
    private CategoryManager manager;

    public CategoryController() {
        setManager(new CategoryManager());
    }
    public void setManager(CategoryManager manager) {
        this.manager = manager;
    }
    public ArrayList<String> getAllCategories() {
        return manager.getAllCategories();
    }
}
