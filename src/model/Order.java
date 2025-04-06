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
    private OrderLine[] orderLines;
    private static final int NB_MAX_PRODUCT = 30;

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
        this.orderLines = new OrderLine[NB_MAX_PRODUCT];
    }

    public String totalPrice() { // Changer type si besoin
        double totalPrice = 0;

        for (OrderLine orderLine : orderLines) {
             totalPrice += orderLine.getQuantity() * orderLine.getUnitPrice();
        }
        String formattedTotalPrice = String.format("%.2f", totalPrice);
        return formattedTotalPrice;
    }

    @Override
    public String toString() {
        return "Commande " + id + " : " + totalPrice() + "€ prise par " + user + " le " + date;
    }
}