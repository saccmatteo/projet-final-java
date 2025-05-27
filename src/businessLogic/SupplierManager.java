package businessLogic;

import dataAccess.SupplierDBAccess;
import interfaces.SupplierDataAccess;

import java.util.ArrayList;

public class SupplierManager {
    private SupplierDataAccess dao;

    public SupplierManager() {
        setDao(new SupplierDBAccess());
    }

    public void setDao(SupplierDBAccess dao) {
        this.dao = dao;
    }

    public String getSupplierPhoneNumberByName(String supplierName) {
        return dao.getSupplierPhoneNumberByName(supplierName);
    }


}
