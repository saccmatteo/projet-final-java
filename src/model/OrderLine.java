package model;

public class OrderLine {
    private Integer quantity;
    private Double unitPrice;
    private Product product;
    private Order order;

    public OrderLine(Integer quantity, Double unitPrice, Product product) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
