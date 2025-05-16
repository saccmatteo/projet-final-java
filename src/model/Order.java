package model;

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
    private OrderLine[] orderLines;
    private static final int NB_MAX_PRODUCT = 30;

    // CONSTRUCTORS
        // Java -> BD
    public Order(LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, String statusLabel, String paymentMethodLabel, User user) {
        this.date = date;
        this.paymentDate = paymentDate;
        this.discountPercentage = discountPercentage;
        this.comment = comment;
        this.isHappyHour = isHappyHour;
        this.statusLabel = statusLabel;
        this.paymentMethodLabel = paymentMethodLabel;
        this.user = user;
        this.orderLines = new OrderLine[NB_MAX_PRODUCT];
    }
        // BD -> Java
        public Order(Integer id, LocalDate date, LocalDate paymentDate, Integer discountPercentage, String comment, Boolean isHappyHour, String statusLabel, String paymentMethodLabel, User user) {
            this.id = id;
            this.date = date;
            this.paymentDate = paymentDate;
            this.discountPercentage = discountPercentage;
            this.comment = comment;
            this.isHappyHour = isHappyHour;
            this.statusLabel = statusLabel;
            this.paymentMethodLabel = paymentMethodLabel;
            this.user = user;
            this.orderLines = new OrderLine[NB_MAX_PRODUCT];
        }
    // Setters
    public void setId(Integer id){
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

    @Override
    public String toString() {
        return "Commande " + id + " : prise par " + user.getFirstName() + " " + user.getLastName() + " le " + date;
    }

    public String totalPrice() { // Changer type si besoin
        double totalPrice = 0;

        for (OrderLine orderLine : orderLines) {
            totalPrice += orderLine.getQuantity() * orderLine.getUnitPrice();
        }
        String formattedTotalPrice = String.format("%.2f", totalPrice);
        return formattedTotalPrice;
    }
}