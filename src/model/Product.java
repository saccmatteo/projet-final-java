package model;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private Integer id;
    private Integer nbInStock;
    private Integer minTreshold;

    private Double price;
    private Double alcoholPercentage;

    private String label;
    private String description;
    private String supplierLabel;
    private String supplierPhoneNumber;
    private String categoryLabel;

    private Boolean isGlutenFree;
    private LocalDate distributionDate;
    private LocalDate lastRestockDate;

    // CONSTRUCTOR
        // Java -> BD -> Pas besoin d'ID grâce à AUTO INCREMENT
    public Product(String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, String supplierPhoneNumber, String categoryLabel) {
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
    public Product(Integer id, String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, String supplierPhoneNumber, String categoryLabel) {
        this(label, price, nbInStock, minTreshold, isGlutenFree, alcoholPercentage, distributionDate, lastRestockDate, description, supplierLabel, supplierPhoneNumber, categoryLabel);
        this.id = id;
    }

    // GETTERS
    public Integer getId() {
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
    public String getSupplierPhoneNumber(){
        return supplierPhoneNumber;
    }
    public String getCategoryLabel() {
        return categoryLabel;
    }

    // METHODES
    @Override
    public String toString() {
        return label + " - " + price + "€ - " + nbInStock + " en stock";
    }

}
