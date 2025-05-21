package model;

import java.time.LocalDate;

public class Order {
    private Integer id;
    private Integer discountPercentage;
    private Double totalPrice;

    private String comment;
    private String statusLabel;
    private String paymentMethodLabel;

    private Boolean isHappyHour;
    private LocalDate date;
    private LocalDate paymentDate;

    private User user;

    // CONSTRUCTORS
        // Java -> BD -> Pas besoin d'ID (AUTO INCREMENT)
    public Order(LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, String statusLabel, String paymentMethodLabel, User user) {
        this.date = date;
        this.paymentDate = paymentDate;
        this.discountPercentage = discountPercentage;
        this.comment = comment;
        this.isHappyHour = isHappyHour;
        this.statusLabel = statusLabel;
        this.paymentMethodLabel = paymentMethodLabel;
        this.user = user;
    }
        // BD -> Java
    public Order(Integer id, LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, String statusLabel, String paymentMethodLabel, User user) {
        this(date, paymentDate, discountPercentage, comment, isHappyHour, statusLabel, paymentMethodLabel, user);
        this.id = id;
    }

    // GETTERS
    public Integer getId() {
        return id;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public Integer getDiscountPercentage() {
        return discountPercentage;
    }
    public String getComment() {
        return comment;
    }
    public Boolean getHappyHour() {
        return isHappyHour;
    }
    public String getStatusLabel() {
        return statusLabel;
    }
    public String getPaymentMethodLabel() {
        return paymentMethodLabel;
    }
    public Integer getUserId() {
        return user.getId();
    }
    public User getUser() {
        return user;
    }

    // SETTERS
    public void setId(Integer id){
        this.id = id;
    }
    public void setTotaPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }

    // METHODES
    public String toString() {
        return "Commande " + id + " (" + String.format("%.2f", totalPrice) + "€) : prise par " + user.getFirstName() + " " + user.getLastName() + " le " + date;
    }
}