package model;

import java.time.LocalDate;

public class AlcoholicDrinksInfos {
    private Integer productId;
    private Integer OlQuantity;
    private Integer orderId;

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
    public String getProductLabel() {
        return productLabel;
    }
    public Integer getOlQuantity() {
        return OlQuantity;
    }
    public LocalDate getOrderDate() {
        return OrderDate;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public Double getAlcoholPercentage() {
        return alcoholPercentage;
    }
}
