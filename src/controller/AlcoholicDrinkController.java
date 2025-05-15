package controller;

import businessLogic.AlcoholicDrinkManager;
import model.AlcoholicDrinksInfos;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlcoholicDrinkController {
    private AlcoholicDrinkManager manager;

    public AlcoholicDrinkController() {
        setManager(new AlcoholicDrinkManager());
    }

    public ArrayList<AlcoholicDrinksInfos> getAlcDrinksBeforeDate(LocalDate date) {
        return manager.getAlcDrinksBeforeDate(date);
    }
    public void setManager(AlcoholicDrinkManager manager) {
        this.manager = manager;
    }
}
