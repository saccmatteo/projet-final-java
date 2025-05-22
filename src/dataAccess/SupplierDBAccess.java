package dataAccess;

import interfaces.SupplierDataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDBAccess implements SupplierDataAccess {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public String getSupplierPhoneNumberByName(String supplierName) {
        String phoneNumber = null;
        try{
            sqlInstruction = "SELECT phone_number FROM supplier WHERE label = ?";
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setString(1, supplierName);
            data = preparedStatement.executeQuery();

            while (data.next()) {
                phoneNumber = CrudUtils.getNullableString(data, "phone_number");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return phoneNumber;
    }
}
