package model;

import exceptions.OrderLineQuantityException;

public class OrderLine {
    private Integer quantity;
    private Double unitPrice;
    private Product product;

    public OrderLine(Integer quantity, Double unitPrice, Product product) throws OrderLineQuantityException {
        setQuantity(quantity);
        this.unitPrice = unitPrice;
        this.product = product;
    }

    // GETTERES
    public Integer getQuantity() {
        return quantity;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public Product getProduct() {
        return product;
    }

    // SETTERS
    public void setQuantity(Integer quantity) throws OrderLineQuantityException {
        if (quantity == null ) {
            throw new OrderLineQuantityException(null, "La quantité est obligatoire.");
        }
        if (quantity <= 0) {
            throw new OrderLineQuantityException(quantity, "La quantité ne peut pas être négative.");
        }
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
