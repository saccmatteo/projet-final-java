package controller;

import businessLogic.SupplierManager;

import java.util.ArrayList;

public class SupplierController {
    private SupplierManager manager;

    public SupplierController() {
        setManager(new SupplierManager());
    }

    public void setManager(SupplierManager manager) {
        this.manager = manager;
    }

    public String getSupplierPhoneNumberByName(String supplierName) {
        return manager.getSupplierPhoneNumberByName(supplierName);
    }
}
