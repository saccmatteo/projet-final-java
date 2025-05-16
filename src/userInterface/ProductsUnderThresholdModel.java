package userInterface;

import model.OrderInfos;
import model.ProductsUnderThreshold;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductsUnderThresholdModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<ProductsUnderThreshold> ProductsUnderThreshold;

    public ProductsUnderThresholdModel(ArrayList<ProductsUnderThreshold> ProductsUnderThreshold) {
        columnNames = new ArrayList<>();
        columnNames.add();
    }
}
