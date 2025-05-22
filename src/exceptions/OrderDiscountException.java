package exceptions;

public class OrderDiscountException extends Exception {
    private Integer wrongDiscount;
    public OrderDiscountException (Integer wrongDiscount, String message) {
        super(message);
        setWrongDiscount(wrongDiscount);
    }

    public void setWrongDiscount(Integer wrongDiscount) {
        this.wrongDiscount = wrongDiscount;
    }
}
