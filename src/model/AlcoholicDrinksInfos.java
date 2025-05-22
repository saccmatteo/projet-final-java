package model;

import java.time.LocalDate;

public class AlcoholicDrinksInfos {
    private Integer productId, OlQuantity, orderId;
    private Double alcoholPercentage;
    private String productLabel;
    private LocalDate OrderDate;

    public AlcoholicDrinksInfos(Integer productId, String productLabel, Integer OlQuantity, LocalDate OrderDate, Integer orderId, Double alcoholPercentage) {
        this.productId = productId;
        this.productLabel = productLabel;
        this.OlQuantity = OlQuantity;
        this.OrderDate = OrderDate;
        this.orderId = orderId;
        this.alcoholPercentage = alcoholPercentage;
    }

    // GETTERS
    public Integer getProductId() {
        return productId;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public Integer getOlQuantity() {
        return OlQuantity;
    }
    public Double getAlcoholPercentage() {
        return alcoholPercentage;
    }
    public String getProductLabel() {
        return productLabel;
    }
    public LocalDate getOrderDate() {
        return OrderDate;
    }
}
