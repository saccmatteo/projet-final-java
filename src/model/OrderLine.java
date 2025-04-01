package model;

public class OrderLine {
    private Integer quantity;
    private Double unitPrice;
    private Product product;

    public OrderLine(Integer quantity, Double unitPrice, Product product) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
    }
}
