package exceptions;

public class ProductAlcoholPercentageException extends Exception {
    private Double wrongPercentage;
    public ProductAlcoholPercentageException (Double wrongPercentage, String message) {
        super(message);
        setWrongPercentage(wrongPercentage);
    }

    public void setWrongPercentage(Double wrongPercentage) {
        this.wrongPercentage = wrongPercentage;
    }
}
