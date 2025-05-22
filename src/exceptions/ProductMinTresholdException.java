package exceptions;

public class ProductMinTresholdException extends Exception {
    private Integer wrongNumber;
    public ProductMinTresholdException (Integer wrongNumber, String message) {
        super(message);
        setWrongNumber(wrongNumber);
    }

    public void setWrongNumber(Integer wrongNumber) {
        this.wrongNumber = wrongNumber;
    }
}
