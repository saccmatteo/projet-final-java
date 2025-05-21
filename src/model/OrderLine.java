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

    // GETTERS
    public Double getUnitPrice() {
        return unitPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Product getProduct() {
        return product;
    }
    public Order getOrder() {
        return order;
    }

    // SETTERS
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // METHODES
    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
    public String toString(){
        return quantity + " " + product.getLabel() + " - " + String.format("%.2f", unitPrice * quantity) + "€";
    }
}
