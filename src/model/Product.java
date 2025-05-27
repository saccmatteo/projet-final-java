package model;

import exceptions.*;

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
    private String supplierPhoneNumber;
    private String categoryLabel;

    // CONSTRUCTOR
        // Java -> BD
    public Product(String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, String supplierPhoneNumber, String categoryLabel)
            throws ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException {
        setLabel(label);
        setPrice(price);
        setNbInStock(nbInStock);
        setMinTreshold(minTreshold);
        this.isGlutenFree = isGlutenFree;
        setAlcoholPercentage(alcoholPercentage);
        this.distributionDate = distributionDate;
        this.lastRestockDate = lastRestockDate;
        setDescription(description);
        setSupplierLabel(supplierLabel);
        setSupplierPhoneNumber(supplierPhoneNumber);
        this.categoryLabel = categoryLabel;
    }
        // BD -> Java
    public Product(Integer id, String label, Double price, Integer nbInStock, Integer minTreshold, Boolean isGlutenFree, Double alcoholPercentage, LocalDate distributionDate, LocalDate lastRestockDate, String description, String supplierLabel, String supplierPhoneNumber, String categoryLabel) throws ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException {
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

    public String getSupplierPhoneNumber(){
        return supplierPhoneNumber;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setLabel(String label) throws ProductLabelException {
        if (label == null || label.trim().isEmpty()) {
            throw new ProductLabelException(label, "Le nom du produit est obligatoire.");
        }
        if (label.length() > 20) {
            throw new ProductLabelException(label, "Le nom du produit ne peut pas dépasser 20 caractères.");
        }
        if (label.trim().matches("\\d+")) {
            throw new ProductLabelException(label, "Le nom du produit ne peut pas être uniquement composé de chiffres.");
        }
        if (!label.matches(".*[a-zA-Z].*")) {
            throw new ProductLabelException(label, "Le nom du produit doit contenir au moins une lettre.");
        }
        this.label = label;
    }

    public void setPrice(Double price) throws ProductPriceException{
        if (price < 0) {
            throw new ProductPriceException(price, "Le prix ne peut pas être négatif.");
        }
        this.price = price;
    }

    public void setNbInStock(Integer nbInStock) throws ProductNbInStockException {
        if (nbInStock < 0) {
            throw new ProductNbInStockException(nbInStock, "Le stock ne peut pas être négatif.");
        }

        this.nbInStock = nbInStock;
    }

    public void setMinTreshold(Integer minTreshold) throws ProductMinTresholdException {
        if (minTreshold < 0) {
            throw new ProductMinTresholdException(minTreshold, "Le seuil avant notification ne peut être négatif.");
        }
        this.minTreshold = minTreshold;
    }

    public void setAlcoholPercentage(Double alcoholPercentage) throws ProductAlcoholPercentageException {
        if (alcoholPercentage != null && (alcoholPercentage < 1 || alcoholPercentage > 100)) {
            throw new ProductAlcoholPercentageException(alcoholPercentage, "Le taux d'alcool doit être entre 1 et 100% ou nul.");
        }
        this.alcoholPercentage = alcoholPercentage;
    }

    public void setDescription(String description) throws ProductDescriptionException {
        if (description != null && description.length() > 300) {
            throw new ProductDescriptionException(description, "La description ne peut pas dépasser 300 caractères.");
        }
        this.description = description;
    }

    public void setSupplierLabel(String supplierLabel) throws ProductSupplierLabelException {
        if (supplierLabel == null || supplierLabel.trim().isEmpty()) {
            throw new ProductSupplierLabelException(supplierLabel, "Le fournisseur est obligatoire.");
        }
        if (supplierLabel.length() > 20) {
            throw new ProductSupplierLabelException(supplierLabel, "Le nom du fournisseur ne peut pas dépasser 20 caractères.");
        }
        if (supplierLabel.trim().matches("\\d+")) {
            throw new ProductSupplierLabelException(supplierLabel, "Le nom du fournisseur ne peut pas être uniquement composé de chiffres.");
        }
        if (!supplierLabel.matches(".*[a-zA-Z].*")) {
            throw new ProductSupplierLabelException(supplierLabel, "Le nom du fournisseur doit contenir au moins une lettre.");
        }
        this.supplierLabel = supplierLabel;
    }

    public void setSupplierPhoneNumber(String supplierPhoneNumber) throws ProductSupplierPhoneNumberException{
        if (supplierPhoneNumber == null || supplierPhoneNumber.trim().isEmpty()) {
            throw new ProductSupplierPhoneNumberException(supplierPhoneNumber, "Le numéro du fournisseur est obligatoire.");
        }
        if (supplierPhoneNumber.length() > 20) {
            throw new ProductSupplierPhoneNumberException(supplierPhoneNumber, "Le numéro du fournisseur ne peut pas dépasser 20 caractères.");
        }
        if (!supplierPhoneNumber.matches("\\d+")) {
            throw new ProductSupplierPhoneNumberException(supplierPhoneNumber, "Le numéro du fournisseur doit contenir uniquement des chiffres.");
        }
        this.supplierPhoneNumber = supplierPhoneNumber;
    }
}
