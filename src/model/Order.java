package model;

import exceptions.OrderCommentException;
import exceptions.OrderDiscountException;

import java.time.LocalDate;

public class Order {
    private Integer id;
    private LocalDate date;
    private LocalDate paymentDate;
    private Integer discountPercentage;
    private String comment;
    private Boolean isHappyHour;
    private String statusLabel;
    private String paymentMethodLabel;
    private User user;

    private Double totalPrice;

    // CONSTRUCTORS
        // Java -> BD
    public Order(LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, String statusLabel, String paymentMethodLabel, User user) throws OrderDiscountException, OrderCommentException {
        this.date = date;
        this.paymentDate = paymentDate;
        setDiscountPercentage(discountPercentage);
        setComment(comment);
        this.isHappyHour = isHappyHour;
        this.statusLabel = statusLabel;
        this.paymentMethodLabel = paymentMethodLabel;
        this.user = user;
    }
        // BD -> Java
    public Order(Integer id, LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, String statusLabel, String paymentMethodLabel, User user) throws OrderDiscountException, OrderCommentException {
        this(date, paymentDate, discountPercentage, comment, isHappyHour, statusLabel, paymentMethodLabel, user);
        this.id = id;
    }
    // Setters
    public void setId(Integer id){
        this.id = id;
    }
    public void setTotaPrice(Double totalPrice){
        this.totalPrice = totalPrice;
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

    public void setDiscountPercentage(Integer discountPercentage) throws OrderDiscountException {
        if (discountPercentage != null && (discountPercentage < 1 || discountPercentage > 100)) {
            throw new OrderDiscountException(discountPercentage, "Le pourcentage de remise doit être compris entre 1 et 100");
        }
        this.discountPercentage = discountPercentage;
    }

    public void setComment(String comment) throws OrderCommentException {
        if (comment != null && comment.length() > 200) {
            throw new OrderCommentException(comment, "Le commentaire ne peut pas dépasser 200 caractères");
        }
        this.comment = comment;
    }

    public String toString() {
        return "Commande " + id + " (" + String.format("%.2f", totalPrice) + "€) : prise par " + user.getFirstName() + " " + user.getLastName() + " le " + date;
    }
}