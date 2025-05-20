package userInterface;

import model.AlcoholicDrinksInfos;

import javax.swing.table.AbstractTableModel;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class AlcoholicDrinksModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<AlcoholicDrinksInfos> alcoholicDrinks;

    public AlcoholicDrinksModel(ArrayList<AlcoholicDrinksInfos> alcoholicDrinks) {
        columnNames = new ArrayList<>();
        columnNames.add("ID boisson");
        columnNames.add("Nom");
        columnNames.add("Pourcentage alcool");
        columnNames.add("Quantité");
        columnNames.add("ID commande");
        columnNames.add("Date commande");
        setAlcoholicDrinks(alcoholicDrinks);
        fireTableDataChanged(); // Pour rafraichir la table au cas où
    }

    public void setAlcoholicDrinks(ArrayList<AlcoholicDrinksInfos> alcoholicDrinks) {
        this.alcoholicDrinks = alcoholicDrinks;
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return alcoholicDrinks.size();
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public Object getValueAt(int row, int column) {
        AlcoholicDrinksInfos alcoholicDrink = alcoholicDrinks.get(row);
        switch (column) {
            case 0:
                return alcoholicDrink.getProductId();
            case 1:
                return alcoholicDrink.getProductLabel();
            case 2:
                return alcoholicDrink.getAlcoholPercentage();
            case 3:
                return alcoholicDrink.getOlQuantity();
            case 4:
                return alcoholicDrink.getOrderId();
            case 5:
                return Date.from(alcoholicDrink.getOrderDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            default:
                return null;
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
            default:
                return null;
        }
    }
}
