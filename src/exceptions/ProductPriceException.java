package exceptions;

public class ProductPriceException extends Exception {
    private Double wrongPrice;
    public ProductPriceException (Double wrongPrice, String message) {
        super(message);
        setWrongPrice(wrongPrice);
    }
    public void setWrongPrice(Double wrongPrice) {
        this.wrongPrice = wrongPrice;
    }
}
