package model;

import java.time.LocalDate;

public class OrderInfos {
    private Integer olQuantity;
    private String orderStatus, userFunction, userFirstName, userLastName;
    private LocalDate orderDate;

    public OrderInfos(LocalDate orderDate, String orderStatus, Integer olQuantity, String userFunction, String userFirstName, String userLastName) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.olQuantity = olQuantity;
        this.userFunction = userFunction;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    // GETTERS
    public Integer getOlQuantity() {
        return olQuantity;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public String getUserFunction() {
        return userFunction;
    }
    public String getUserFirstName() {
        return userFirstName;
    }
    public String getUserLastName() {
        return userLastName;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
}
