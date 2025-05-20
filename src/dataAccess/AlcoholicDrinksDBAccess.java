package dataAccess;

import interfaces.AlcoholicDrinkDataAccess;
import model.AlcoholicDrinksInfos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AlcoholicDrinksDBAccess implements AlcoholicDrinkDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    ResultSet data;

    public ArrayList<AlcoholicDrinksInfos> getAlcDrinksBeforeDate(LocalDate date) {
        ArrayList<AlcoholicDrinksInfos> alcoholicDrinks = new ArrayList<>();
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        try {
            sqlInstruction =
                    "SELECT " +
                    "  p.id AS product_id, " +
                    "  p.label AS product_label, " +
                    "  p.alcohol_percentage, " +
                    "  ol.quantity, " +
                    "  o.id AS order_id, " +
                    "  o.order_date " +
                    "FROM product p " +
                    "INNER JOIN orderLine ol ON p.id = ol.product_id " +
                    "INNER JOIN `order` o ON ol.order_id = o.id " +
                    "WHERE p.category_label = ? " +
                    "AND o.order_date >= ?;";

            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, "Boisson alcoolisée");
            preparedStatement.setDate(2, sqlDate);
            data = preparedStatement.executeQuery();

            while(data.next()) {
                // Ne pas vérifier si c'est null car ces champs ne peuvent dans tous les cas pas l'être
                AlcoholicDrinksInfos newAlcoholicDrink = new AlcoholicDrinksInfos(
                        data.getInt("product_id"),
                        data.getString("product_label"),
                        data.getInt("ol.quantity"),
                        data.getDate("o.order_date").toLocalDate(),
                        data.getInt("order_id"),
                        data.getDouble("p.alcohol_percentage")
                );
                alcoholicDrinks.add(newAlcoholicDrink);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alcoholicDrinks;
    }
}
