package businessLogic;

import dataAccess.SupplierDBAccess;
import exceptions.DAOException;
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

    public String getSupplierPhoneNumberByName(String supplierName) throws DAOException {
        return dao.getSupplierPhoneNumberByName(supplierName);
    }


}
