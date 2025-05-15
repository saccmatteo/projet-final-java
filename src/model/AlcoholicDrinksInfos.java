package model;

import java.time.LocalDate;

public class AlcoholicDrinksInfos {
    private int productId;
    private String productLabel;
    private int OlQuantity;
    private LocalDate OrderDate;
    private int orderId;
    private double alcoholPercentage;

    public AlcoholicDrinksInfos(int productId, String productLabel, int OlQuantity, LocalDate OrderDate, int orderId) {
        this.productId = productId;
        this.productLabel = productLabel;
        this.OlQuantity = OlQuantity;
        this.OrderDate = OrderDate;
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public int getOlQuantity() {
        return OlQuantity;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }
}
