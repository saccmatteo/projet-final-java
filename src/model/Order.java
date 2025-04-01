package model;

import java.time.LocalDate;

public class Order {
    private Integer id;
    private LocalDate date;
    private LocalDate paymentDate;
    private Integer discountPercentage;
    private String comment;
    private Boolean isHappyHour;
    private Status status;
    private PaymentMethod paymentMethod;
    private User user;

    public Order(Integer id, LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, Status status, PaymentMethod paymentMethod, User user) {
        this.id = id;
        this.date = date;
        this.paymentDate = paymentDate;
        this.discountPercentage = discountPercentage;
        this.comment = comment;
        this.isHappyHour = isHappyHour;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.user = user;
    }
}
