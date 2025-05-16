package userInterface;

import model.OrderInfos;
import model.ProductsUnderThreshold;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductsUnderThresholdModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<ProductsUnderThreshold> ProductsUnderThreshold;

    public ProductsUnderThresholdModel(ArrayList<ProductsUnderThreshold> productsUnderThreshold) {
        columnNames = new ArrayList<>();
        columnNames.add("ID Produit");
        columnNames.add("Nom");
        columnNames.add("Date dernier restock");
        columnNames.add("Catégorie");
        columnNames.add("Fournisseur");
        columnNames.add("Telephone Fournisseur");
        setProductsUnderThreshold(productsUnderThreshold);
    }

    public void setProductsUnderThreshold(ArrayList<ProductsUnderThreshold> productsUnderThreshold) {
        ProductsUnderThreshold = productsUnderThreshold;
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return ProductsUnderThreshold.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        ProductsUnderThreshold productsUnderThreshold = ProductsUnderThreshold.get(row);
        switch (col) {
            case 0:
                return productsUnderThreshold.getProductId();
            case 1:
                return productsUnderThreshold.getProductLabel();
            case 2:
                return productsUnderThreshold.getProductLastRestockDate();
            case 3:
                return productsUnderThreshold.getCategoryLabel();
            case 4:
                return productsUnderThreshold.getSupplierLabel();
            case 5:
                return productsUnderThreshold.getSupplierPhoneNumber();
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
                return Integer.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            default:
                return null;
        }
    }
}
