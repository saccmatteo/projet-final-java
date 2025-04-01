package model;

public class PaymentMethod {
    private String label;
    private Order [] orders;
    private static final Integer MAX_ORDERS_NB = 5;

    public PaymentMethod(String label) {
        this.label = label;
        orders = new Order[MAX_ORDERS_NB];
    }
}
