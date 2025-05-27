package model;

import exceptions.OrderLineQuantityException;

public class OrderLine {
    private Integer quantity;
    private Double unitPrice;
    private Product product;
    private Order order;

    public OrderLine(Integer quantity, Double unitPrice, Product product) throws OrderLineQuantityException {
        setQuantity(quantity);
        this.unitPrice = unitPrice;
        this.product = product;
    }

    // Getters
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

    // Setters
    public void setQuantity(Integer quantity) throws OrderLineQuantityException {
        if (quantity == null ) {
            throw new OrderLineQuantityException(null, "La quantité est obligatoire.");
        }
        if (quantity <= 0) {
            throw new OrderLineQuantityException(quantity, "La quantité ne peut pas être négative.");
        }
        this.quantity = quantity;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
    // Methodes
    public String toString(){
        return quantity + " " + product.getLabel() + " - " + String.format("%.2f", unitPrice * quantity) + "€";
    }
}
