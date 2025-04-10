package model;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private Integer id;
    private String label;
    private Double price;
    private Integer nbInStock;
    private Integer minTreshold;
    private Boolean isGlutenFree;
    private Double alcoholPercentage;
    private LocalDate distributionDate;
    private LocalDate lastRestockDate;
    private String description;
    private String supplierLabel;
    private int supplierPhoneNumber;
    private String categoryLabel;

    public Product(Integer id, String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, int supplierPhoneNumber, String categoryLabel) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.nbInStock = nbInStock;
        this.minTreshold = minTreshold;
        this.isGlutenFree = isGlutenFree;
        this.alcoholPercentage = alcoholPercentage;
        this.distributionDate = distributionDate;
        this.description = description;
        this.supplierLabel = supplierLabel;
        this.supplierPhoneNumber = supplierPhoneNumber;
        this.categoryLabel = categoryLabel;
    }

    @Override
    public String toString() {
        return label + " - " + price + "€";
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public String getLabel() {
        return label;
    }
}
