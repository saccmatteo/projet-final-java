package businessLogic;

import dataAccess.CategoryDBAccess;
import interfaces.CategoryDataAccess;

import java.util.ArrayList;

public class CategoryManager {
    private CategoryDataAccess dao;

    public CategoryManager() {
        setDao(new CategoryDBAccess());
    }

    public void setDao(CategoryDBAccess categoryDBAccess) {
        this.dao = categoryDBAccess;
    }

    public ArrayList<String> getAllCategories() {
        return dao.getAllCategories();
    }
}
