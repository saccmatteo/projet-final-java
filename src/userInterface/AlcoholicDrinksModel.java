package userInterface;

import model.AlcoholicDrinksInfos;
import model.Product;

import javax.swing.table.AbstractTableModel;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class AlcoholicDrinksModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<AlcoholicDrinksInfos> AlcoholicDrinks;

    public AlcoholicDrinksModel(ArrayList<AlcoholicDrinksInfos> alcoholicDrinks) {
        columnNames = new ArrayList<>();
        columnNames.add("ID boisson");
        columnNames.add("Nom");
        columnNames.add("Pourcentage alcool");
        columnNames.add("Quantité");
        columnNames.add("ID commande");
        columnNames.add("Date commande");
        // Continuer pour afficher les colonnes des ORDERS et des ORDERLINES
        setAlcoholicDrinks(alcoholicDrinks);
    }

    public void setAlcoholicDrinks(ArrayList<AlcoholicDrinksInfos> alcoholicDrinks) {
        AlcoholicDrinks = alcoholicDrinks;
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return AlcoholicDrinks.size();
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public Object getValueAt(int row, int column) {
        AlcoholicDrinksInfos alcoholicDrink = AlcoholicDrinks.get(row);
        switch (column) {
            case 0 :
                return alcoholicDrink.getProductId();
            case 1 :
                return alcoholicDrink.getProductLabel();
            case 2 :
                return alcoholicDrink.getAlcoholPercentage();
            case 3 :
                return alcoholicDrink.getOlQuantity();
            case 4 :
                return alcoholicDrink.getOrderId();
            case 5 :
                return Date.from(alcoholicDrink.getOrderDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            default :
                return null; // à voir si on garde ça
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Double.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return Date.class;
            default :
                return null;
        }
    }
}
