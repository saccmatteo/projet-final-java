package interfaces;

import model.AlcoholicDrinksInfos;
import model.Product;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AlcoholicDrinkDataAccess {
    ArrayList<AlcoholicDrinksInfos> getAlcDrinksBeforeDate(LocalDate date);
}
