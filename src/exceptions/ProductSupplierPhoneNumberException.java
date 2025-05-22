package exceptions;

public class ProductSupplierPhoneNumberException extends Exception {
    private String wrongPhoneNumber;
    public ProductSupplierPhoneNumberException (String wrongPhoneNumber, String message) {
        super(message);
        setWrongPhoneNumber(wrongPhoneNumber);
    }

    public void setWrongPhoneNumber(String wrongPhoneNumber) {
        this.wrongPhoneNumber = wrongPhoneNumber;
    }
}
