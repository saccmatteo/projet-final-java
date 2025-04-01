package model;

public class Supplier {
    private String label;
    private String phoneNumber;
    private Product [] products;
    private static final Integer MAX_PRODUCTS_NB = 10;

    public Supplier(String label, String phoneNumber) {
        this.label = label;
        this.phoneNumber = phoneNumber;
        this.products = new Product[MAX_PRODUCTS_NB];
    }
}
