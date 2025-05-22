package model;

import exceptions.OrderCommentException;
import exceptions.OrderDiscountException;

import java.time.LocalDate;

public class Order {
    private Integer id, discountPercentage;
    private Double totalPrice;
    private String comment, statusLabel, paymentMethodLabel;
    private Boolean isHappyHour;
    private LocalDate date, paymentDate;
    private User user;

    // CONSTRUCTORS
        // Java -> BD Pour les encoder les commandes (AUTO-INCREMENT)
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

    // GETTERS
    public Integer getId() {
        return id;
    }
    public Integer getUserId() {
        return user.getId();
    }
    public Integer getDiscountPercentage() {
        return discountPercentage;
    }
    public String getComment() {
        return comment;
    }
    public String getStatusLabel() {
        return statusLabel;
    }
    public String getPaymentMethodLabel() {
        return paymentMethodLabel;
    }
    public Boolean getHappyHour() {
        return isHappyHour;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
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

    // METHODES
    public String toString() {
        return "Commande " + id + " (" + String.format("%.2f", totalPrice) + "€) : prise par " + user.getFirstName() + " " + user.getLastName() + " le " + date;
    }
}