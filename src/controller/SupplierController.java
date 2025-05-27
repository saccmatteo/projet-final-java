package controller;

import businessLogic.SupplierManager;
import exceptions.DAOException;

import java.util.ArrayList;

public class SupplierController {
    private SupplierManager manager;

    public SupplierController() {
        setManager(new SupplierManager());
    }

    public void setManager(SupplierManager manager) {
        this.manager = manager;
    }

    public String getSupplierPhoneNumberByName(String supplierName) throws DAOException {
        return manager.getSupplierPhoneNumberByName(supplierName);
    }
}
