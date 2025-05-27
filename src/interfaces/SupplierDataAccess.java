package interfaces;


import exceptions.DAOException;

import java.util.ArrayList;

public interface SupplierDataAccess {
    String getSupplierPhoneNumberByName(String supplierName) throws DAOException;
}
