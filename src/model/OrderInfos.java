package model;

import java.time.LocalDate;

public class OrderInfos {
    private LocalDate orderDate;
    private String orderStatus;
    private Integer olQuantity;
    private String userFunction;
    private String userFirstName;
    private String userLastName;

    public OrderInfos(LocalDate orderDate, String orderStatus, Integer olQuantity, String userFunction, String userFirstName, String userLastName) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.olQuantity = olQuantity;
        this.userFunction = userFunction;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Integer getOlQuantity() {
        return olQuantity;
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
}
