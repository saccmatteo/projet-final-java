package exceptions;

public class ProductNbInStockException extends Exception {
    private Integer wrongNumber;
    public ProductNbInStockException (Integer wrongNumber, String message) {
        super(message);
        setWrongNumber(wrongNumber);
    }

    public void setWrongNumber(Integer wrongNumber) {
        this.wrongNumber = wrongNumber;
    }
}
