package businessLogic;

import dataAccess.AlcoholicDrinksDBAccess;
import interfaces.AlcoholicDrinkDataAccess;
import model.AlcoholicDrinksInfos;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlcoholicDrinkManager {
    private AlcoholicDrinkDataAccess dao;

    public AlcoholicDrinkManager() {
        setDao(new AlcoholicDrinksDBAccess());
    }

    public void setDao(AlcoholicDrinksDBAccess dao) {
        this.dao = dao;
    }

    public ArrayList<AlcoholicDrinksInfos> getAlcDrinksBeforeDate(LocalDate date) {
        return dao.getAlcDrinksBeforeDate(date);
    }
}
