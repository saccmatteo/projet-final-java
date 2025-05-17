package userInterface;

import model.OrderInfos;

import javax.swing.table.AbstractTableModel;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class OrderInfosModel extends AbstractTableModel {
    ArrayList<String> columnNames;
    ArrayList<OrderInfos> ordersInfos;

    public OrderInfosModel(ArrayList<OrderInfos> ordersInfos) {
        columnNames = new ArrayList<>();
        columnNames.add("Prénom utilisateur");
        columnNames.add("Nom utilisateur");
        columnNames.add("Fonction utilisateur");
        columnNames.add("Quantité");
        columnNames.add("Date commande");
        columnNames.add("Statut commande");
        setOrdersInfos(ordersInfos);
    }

    public void setOrdersInfos(ArrayList<OrderInfos> ordersInfos) {
        this.ordersInfos = ordersInfos;
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return ordersInfos.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        OrderInfos orderInfo = ordersInfos.get(row);
        switch (col) {
            case 0:
                return orderInfo.getUserFirstName();
            case 1:
                return orderInfo.getUserLastName();
            case 2:
                return orderInfo.getUserFunction();
            case 3:
                return orderInfo.getOlQuantity();
            case 4:
                return Date.from(orderInfo.getOrderDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            case 5:
                return orderInfo.getOrderStatus();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Integer.class;
            case 4:
                return Date.class;
            case 5:
                return String.class;
            default:
                return null;
        }
    }
}
