package exceptions;

public class OrderLineQuantityException extends Exception {
    private Integer wrongQuantity;
    public OrderLineQuantityException (Integer wrongQuantity, String message) {
        super(message);
        setWrongQuantity(wrongQuantity);
    }

    public void setWrongQuantity(Integer wrongQuantity) {
        this.wrongQuantity = wrongQuantity;
    }
}
