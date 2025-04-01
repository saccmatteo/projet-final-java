package model;

public class Category {
    private String label;
    private Product [] products;
    private static final Integer MAX_PRODUCTS_NB = 10;

    public Category(String label) {
        this.label = label;
        products = new Product[MAX_PRODUCTS_NB];
    }
}
