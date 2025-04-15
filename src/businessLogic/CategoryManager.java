package businessLogic;

import dataAccess.CategoryDBAccess;
import model.CategoryDataAccess;

import java.util.ArrayList;

public class CategoryManager {
    private CategoryDataAccess dao;
    public CategoryManager() {
        setDao(new CategoryDBAccess());
    }
    public ArrayList<String> getAllCategories() {
        return dao.getAllCategories();
    }
    public void setDao(CategoryDBAccess categoryDBAccess) {
        this.dao = categoryDBAccess;
    }
}
