package businessLogic;

import dataAccess.AlchoholicDrinksDBAccess;
import interfaces.AlcoholicDrinkDataAccess;
import model.AlcoholicDrinksInfos;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlcoholicDrinkManager {
    private AlcoholicDrinkDataAccess dao;

    public AlcoholicDrinkManager() {
        setDao(new AlchoholicDrinksDBAccess());
    }
    public ArrayList<AlcoholicDrinksInfos> getAlcDrinksBeforeDate(LocalDate date) {
        return dao.getAlcDrinksBeforeDate(date);
    }
    public void setDao(AlcoholicDrinkDataAccess dao) {
        this.dao = dao;
    }
}
