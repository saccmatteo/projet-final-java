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
    private Supplier supplier;
    private Category category;

    public Product(Integer id, String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, String description, Supplier supplier, Category category) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.nbInStock = nbInStock;
        this.minTreshold = minTreshold;
        this.isGlutenFree = isGlutenFree;
        this.alcoholPercentage = alcoholPercentage;
        this.distributionDate = distributionDate;
        this.description = description;
        this.supplier = supplier;
        this.category = category;
    }
}
