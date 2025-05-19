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
    private Integer supplierPhoneNumber;
    private String categoryLabel;

    // CONSTRUCTOR
        // Java -> BD
    public Product(String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, Integer supplierPhoneNumber, String categoryLabel) {
        this.label = label;
        this.price = price;
        this.nbInStock = nbInStock;
        this.minTreshold = minTreshold;
        this.isGlutenFree = isGlutenFree;
        this.alcoholPercentage = alcoholPercentage;
        this.distributionDate = distributionDate;
        this.lastRestockDate = lastRestockDate;
        this.description = description;
        this.supplierLabel = supplierLabel;
        this.supplierPhoneNumber = supplierPhoneNumber;
        this.categoryLabel = categoryLabel;
    }
        // BD -> Java
    public Product(Integer id, String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, int supplierPhoneNumber, String categoryLabel) {
        this(label, price, nbInStock, minTreshold, isGlutenFree, alcoholPercentage, distributionDate, lastRestockDate, description, supplierLabel, supplierPhoneNumber, categoryLabel);
        this.id = id;
    }
    @Override
    public String toString() {
        return label + " - " + price + "€ - " + nbInStock + " en stock";
    }
    // GETTERS
    public Integer getId() {
        if (id == null)
            return -1;
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getNbInStock() {
        return nbInStock;
    }

    public Integer getMinTreshold() {
        return minTreshold;
    }

    public Boolean getGlutenFree() {
        return isGlutenFree;
    }

    public LocalDate getDistributionDate() {
        return distributionDate;
    }

    public LocalDate getLastRestockDate() {
        return lastRestockDate;
    }

    public Double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public String getDescription() {
        return description;
    }

    public String getSupplierLabel(){
        return supplierLabel;
    }

    public int getSupplierPhoneNumber(){
        return supplierPhoneNumber;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

}
